package com.degas.simplemvc.xmlbean;

import com.degas.simplemvc.configuration.model.ControllerConfig;
import com.degas.simplemvc.configuration.model.Forward;
import com.degas.simplemvc.configuration.model.SimpleMvcConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;


/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 16.07.13
 * Time: 13:49
 */

@RunWith(JUnit4.class)
public class ConfigurationImportMvcTest {

    /** JAXB example file
     *
     * <simple-mvc-config>
     *    <controller-config>
     *         <url>/home</url>
     *         <type>com.degas.simplemvc.controllers.HomeController</type>
     *         <forward>
     *             <name>homePage</name>
     *             <pagesPath>/home.jsp</pagesPath>
     *         </forward>
     *         <forward>
     *             <name>loginPage</name>
     *             <pagesPath>/index.jsp</pagesPath>
     *         </forward>
     *     </controller-config>
     * </simple-mvc-config>
     *
     * */

    public SimpleMvcConfig simpleMvcConfig;

    @Before
    public void initModelData(){

        // one controller
        Forward homePage = new Forward();
        homePage.setName("homePage");
        homePage.setPagesPath("/home.jsp");

        Forward loginPage = new Forward();
        loginPage.setName("loginPage");
        loginPage.setPagesPath("/index.jsp");

        Set<Forward> forwardUrlHome = new HashSet<Forward>();
        forwardUrlHome.add(loginPage);
        forwardUrlHome.add(homePage);

        ControllerConfig controllerUrlHome = new ControllerConfig();
        controllerUrlHome.setUrl("/home");
        controllerUrlHome.setType("com.degas.simplemvc.controllers.HomeController");
        controllerUrlHome.setForward(forwardUrlHome);

        // two controller
        Forward contactPage = new Forward();
        contactPage.setName("contactPage");
        contactPage.setPagesPath("/contact.jsp");

        Set<Forward> forwardUrlContact = new HashSet<Forward>();
        forwardUrlContact.add(contactPage);

        ControllerConfig controllerUrlContact = new ControllerConfig();
        controllerUrlContact.setUrl("/contact");
        controllerUrlContact.setType("com.degas.simplemvc.controllers.ContactController");
        controllerUrlContact.setForward(forwardUrlContact);

        //fake controller
        ControllerConfig controllerUrlHomeTwin = new ControllerConfig();
        controllerUrlHomeTwin.setUrl("/home");
        controllerUrlHomeTwin.setType("com.degas.simplemvc.controllers.HomeController");
        controllerUrlHomeTwin.setForward(forwardUrlHome);

        // same type or different url
        ControllerConfig controllerOrgUrlContact = new ControllerConfig();
        controllerOrgUrlContact.setUrl("/orgcontact");
        controllerOrgUrlContact.setType("com.degas.simplemvc.controllers.ContactController");
        controllerOrgUrlContact.setForward(forwardUrlContact);

        // ...
        Set<ControllerConfig> controllerConfigs = new HashSet<ControllerConfig>();
        controllerConfigs.add(controllerUrlHome);
        controllerConfigs.add(controllerUrlContact);
        controllerConfigs.add(controllerUrlHomeTwin);
        controllerConfigs.add(controllerOrgUrlContact);

        simpleMvcConfig = new SimpleMvcConfig();
        simpleMvcConfig.setControllerConfig(controllerConfigs);

    }

    @Test
    public void jaxbProcessingTest() throws Exception {

        final JAXBContext jaxbContext;
        final Marshaller marshaller;
        final Unmarshaller unmarshaller;
        final ByteArrayOutputStream byteArrayOutputStream;
        final ByteArrayInputStream byteArrayInputStream;

        // marshaller
        jaxbContext = JAXBContext.newInstance(SimpleMvcConfig.class);
        marshaller = jaxbContext.createMarshaller();
        byteArrayOutputStream = new ByteArrayOutputStream();
        marshaller.marshal(simpleMvcConfig, byteArrayOutputStream);
        //marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // unmarshaller
        unmarshaller = jaxbContext.createUnmarshaller();
        byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        SimpleMvcConfig outSimpleMvcConfig = (SimpleMvcConfig)unmarshaller.unmarshal(byteArrayInputStream);

        Assert.assertNotNull(outSimpleMvcConfig);

        // the same controller
        Assert.assertFalse(checkSameController(outSimpleMvcConfig));

        // more than one forward
        Assert.assertTrue(isMoreForward(outSimpleMvcConfig));

        // not no identical forward
        Assert.assertFalse(checkSameForward(outSimpleMvcConfig));

        //same type
        Assert.assertTrue(checkSameControllerType(outSimpleMvcConfig));

        Assert.assertTrue(checkParsJAXB(outSimpleMvcConfig));

    }

    public boolean isMoreForward(SimpleMvcConfig outSimpleMvcConfig){
        for(ControllerConfig config : outSimpleMvcConfig.getControllerConfig() ){
            if(config.getForward().size() > 1){
                return true;
            }
        }
        return false;
    }

    public boolean checkSameForward(SimpleMvcConfig outSimpleMvcConfig){
        for(ControllerConfig config : outSimpleMvcConfig.getControllerConfig()){
            for(Forward forward : config.getForward()){
                int count = 0;
                for(Forward forward1 : config.getForward()){
                    if(forward.equals(forward1)){
                        count++;
                        if(count > 1) return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean checkSameControllerType(SimpleMvcConfig outSimpleMvcConfig){
        for(ControllerConfig config : outSimpleMvcConfig.getControllerConfig()){
            for(ControllerConfig configFake : outSimpleMvcConfig.getControllerConfig()){
                if(!config.getUrl().equals(configFake.getUrl())){
                    if(config.getType().equals(configFake.getType())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkSameController(SimpleMvcConfig outSimpleMvcConfig){

        for(ControllerConfig config : outSimpleMvcConfig.getControllerConfig()){
            int count = 0;
            for(ControllerConfig config1 : outSimpleMvcConfig.getControllerConfig()){
                if(config.getUrl().equals(config1.getUrl())){
                    count++;
                    if(count > 1){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkParsJAXB(SimpleMvcConfig outSimpleMvcConfig){
        for(ControllerConfig config : outSimpleMvcConfig.getControllerConfig()){
            if(config.getUrl().equals("/home")){
                if(config.getForward().size() == 2){
                    for(Forward forward : config.getForward()){
                        if(forward.getName().equals("loginPage")){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}

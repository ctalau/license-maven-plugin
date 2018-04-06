package org.codehaus.mojo.license.patch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.mojo.license.api.DefaultThirdPartyTool;
import org.codehaus.mojo.license.api.ThirdPartyTool;
import org.codehaus.mojo.license.model.LicenseMap;
import org.codehaus.mojo.license.patch.model.Component;
import org.codehaus.mojo.license.patch.model.ThirdParty;
import org.codehaus.plexus.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 * Override the default tool that handles 3rd party deps.
 */
@org.codehaus.plexus.component.annotations.Component( 
    role = ThirdPartyTool.class, hint = "default" )
public class OxygenThirdPartyTool extends DefaultThirdPartyTool {
  
  @Override
  public void writeThirdPartyFile(LicenseMap licenseMap, File thirdPartyFile, boolean verbose, String encoding,
      String lineFormat) throws IOException {
    Logger log = getLogger();
    if (thirdPartyFile.exists()) {
      ThirdParty existingDeps = loadExistingDeps(thirdPartyFile);
      log.info("Existing deps: " + existingDeps.toString());
    }
    super.writeThirdPartyFile(licenseMap, thirdPartyFile, verbose, encoding, lineFormat);
  }
  
  /**
   * Load the existing deps.
   * 
   * @param thirdPartyFile The file to load from.
   * 
   * @return The model.
   */
  private ThirdParty loadExistingDeps(File thirdPartyFile) {
    ThirdParty thirdParty = new ThirdParty();
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder(); 
      Document doc = db.parse(thirdPartyFile);
      
      Element thirdPartyElem = getChildElements(doc).get(0);
      for (Element componentElem : getChildElements(thirdPartyElem)) {
        thirdParty.getComponents().add(createComponent(componentElem));
      }
      
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return thirdParty;
  }
  
  /**
   * Get the child elements of a node.
   * 
   * @param node The node.
   * 
   * @return The elements.
   */
  private List<Element> getChildElements(Node node) {
    List<Element> children = new ArrayList<Element>();
    for (int j = 0; j < node.getChildNodes().getLength(); j++) {
      Node candidate = node.getChildNodes().item(j);
      if (candidate.getNodeType() == Node.ELEMENT_NODE) {
        children.add((Element) candidate);
      }
    }
    return children;
  }

  /**
   * Creates a component.
   * 
   * @param componentElem The XML element.
   * 
   * @return The model.
   */
  private Component createComponent(Node componentElem) {
    Component component = new Component();
    
    // Attributes
    NamedNodeMap attributes = componentElem.getAttributes();
    component.setId(attributes.getNamedItem("id").getTextContent());
    Node draftAttr = attributes.getNamedItem("draft");
    if (draftAttr != null) {
      component.setDraft(true);
    }
    
    // Elements
    List<Element> items = getChildElements(componentElem);
    for (Element item : items) {
      if (item.getNodeName().equals("name")) {
        component.setName(item.getTextContent());
      } else if (item.getNodeName().equals("version")) {
        component.setVersion(item.getTextContent());
      }
    }
    
    return component;
  }
}

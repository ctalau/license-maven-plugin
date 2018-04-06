package org.codehaus.mojo.license.patch.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a third-party-components.xml file.
 * 
 * @author cristi_talau@sync.ro
 */
public class ThirdParty {
  /**
   * The list of components.
   */
  private final List<Component> components = new ArrayList<Component>();
  
  /**
   * @return the components list.
   */
  public List<Component> getComponents() {
    return components;
  }
  
  @Override
  public String toString() {
    return components.toString();
  }
}

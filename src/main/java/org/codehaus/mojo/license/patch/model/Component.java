package org.codehaus.mojo.license.patch.model;

/**
 * Model for a 3rd party component. 
 * 
 * @author cristi_talau@sync.ro
 */
public class Component {

  private String id;
  private boolean draft;
  private String name;
  private String version;
  private ProjectInfo projectInfo;
  private License license;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  
  public String getVersion() {
    return version;
  }
  public void setVersion(String version) {
    this.version = version;
  }
  
  public ProjectInfo getProjectInfo() {
    return projectInfo;
  }
  public void setProjectInfo(ProjectInfo projectInfo) {
    this.projectInfo = projectInfo;
  }
  
  public License getLicense() {
    return license;
  }
  public void setLicense(License license) {
    this.license = license;
  }
  
  public boolean isDraft() {
    return draft;
  }
  public void setDraft(boolean draft) {
    this.draft = draft;
  }
  
  @Override
  public String toString() {
    return name;
  }
}

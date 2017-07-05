package org.wei86609.osmanthus.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Rule;
import org.wei86609.osmanthus.node.ruleset.RuleSet;

import com.thoughtworks.xstream.XStream;

public class FileRuleSetTranslator implements NodeTranslator<String,Node>{

    private String ruleSetFolder;

    private String ruleSetFolderName="rule";

    private String ruleFileExtension=".xml";

    public String getRuleFileExtension() {
        return ruleFileExtension;
    }

    public void setRuleFileExtension(String ruleFileExtension) {
        this.ruleFileExtension = ruleFileExtension;
    }

    public String getRuleSetFolder() {
        return ruleSetFolder;
    }

    public void setRuleSetFolder(String ruleSetFolder) {
        this.ruleSetFolder = ruleSetFolder;
    }

    public String getRuleSetFolderName() {
        return ruleSetFolderName;
    }

    public void setRuleSetFolderName(String ruleSetFolderName) {
        this.ruleSetFolderName = ruleSetFolderName;
    }

    public Map<String,Node> getNodes()throws Exception{
        Map<String,Node> ruleMaps=new HashMap<String,Node>();
        File file=null;
        if(StringUtils.isEmpty(ruleSetFolder)){
            URL url=FileRuleSetTranslator.class.getClassLoader().getResource(ruleSetFolderName);
            file = new File(url.getFile());
        }else{
            file=new File(ruleSetFolder);
        }
        File[] rsf=file.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.endsWith(ruleFileExtension);
            }
        });
        for(int i=0;i<rsf.length;i++){
            Node node=loadRuleSetFromFile(rsf[i]);
            if(node!=null){
                ruleMaps.put(node.getId(), node);
            }
        }
        return ruleMaps;
    }

    private Node loadRuleSetFromFile(File xmlfile) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{RuleSet.class,Rule.class});
        return (Node)xs.fromXML(new FileInputStream(xmlfile));
    }

    public Node getNode(String nodeId) throws Exception {
        return getNodes().get(nodeId);
    }

}

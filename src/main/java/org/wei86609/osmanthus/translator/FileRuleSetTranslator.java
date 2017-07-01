package org.wei86609.osmanthus.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.wei86609.osmanthus.node.Node;
import org.wei86609.osmanthus.node.Rule;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.ruleset.CardRuleSet;
import org.wei86609.osmanthus.node.ruleset.GeneralRuleSet;

import com.thoughtworks.xstream.XStream;

public class FileRuleSetTranslator implements NodeTranslator<Node>{

    private String ruleSetFolder;

    private String ruleSetFolderName="rule";

    private String ruleFileExtension=".rs";

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

    public List<Node> getNodes()throws Exception{
        List<Node> ruleSetList=new ArrayList<Node>();
        File file=null;
        if(StringUtils.isEmpty(ruleSetFolder)){
            URL url=GeneralRuleSetExecutor.class.getClassLoader().getResource(ruleSetFolderName);
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
                ruleSetList.add(node);
            }
        }
        return ruleSetList;
    }

    private Node loadRuleSetFromFile(File xmlfile) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{GeneralRuleSet.class,Rule.class,CardRuleSet.class});
        return (Node)xs.fromXML(new FileInputStream(xmlfile));
    }

    public Node getNode(String nodeId) throws Exception {
        List<Node> nodes=getNodes();
        for(Node node:nodes){
            if(node!=null && node.getId()!=null && node.getId().equals(nodeId)){
                return node;
            }
        }
        return null;
    }

}

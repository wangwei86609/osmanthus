package org.wei86609.osmanthus.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.wei86609.osmanthus.node.Constraint;
import org.wei86609.osmanthus.node.End;
import org.wei86609.osmanthus.node.Flow;
import org.wei86609.osmanthus.node.Merge;
import org.wei86609.osmanthus.node.Parallel;
import org.wei86609.osmanthus.node.Rule;
import org.wei86609.osmanthus.node.Split;
import org.wei86609.osmanthus.node.Start;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.ruleset.RuleSet;

import com.thoughtworks.xstream.XStream;

public class FlowFileTranslator implements NodeTranslator<String,Flow>{

    private String flowFolder;

    private String flowFolderName="flow";

    private String flowFileExtension=".xml";

    public String getFlowFolder() {
        return flowFolder;
    }

    public void setFlowFolder(String flowFolder) {
        this.flowFolder = flowFolder;
    }

    public String getFlowFolderName() {
        return flowFolderName;
    }

    public void setFlowFolderName(String flowFolderName) {
        this.flowFolderName = flowFolderName;
    }

    public String getFlowFileExtension() {
        return flowFileExtension;
    }

    public void setFlowFileExtension(String flowFileExtension) {
        this.flowFileExtension = flowFileExtension;
    }

    public Map<String,Flow> getNodes()throws Exception{
        Map<String,Flow> flowMaps=new HashMap<String,Flow>();
        File file=null;
        if(StringUtils.isEmpty(flowFolder)){
            URL url=GeneralRuleSetExecutor.class.getClassLoader().getResource(flowFolderName);
            file = new File(url.getFile());
        }else{
            file=new File(flowFolder);
        }
        File[] rsf=file.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.endsWith(flowFileExtension);
            }
        });
        for(int i=0;i<rsf.length;i++){
            Flow flow=loadFlowFromFile(rsf[i]);
            if(flow!=null){
                flowMaps.put(flow.getId(), flow);
            }
        }
        return flowMaps;
    }

    private Flow loadFlowFromFile(File xmlfile) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Flow.class,Start.class,End.class,RuleSet.class,Rule.class,Split.class,Constraint.class,Parallel.class,Merge.class});
        return (Flow)xs.fromXML(new FileInputStream(xmlfile));
    }

    public Flow getNode(String flowId) throws Exception {
        return getNodes().get(flowId);
    }

}

package org.wei86609.osmanthus.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.wei86609.osmanthus.node.Constraint;
import org.wei86609.osmanthus.node.End;
import org.wei86609.osmanthus.node.Flow;
import org.wei86609.osmanthus.node.Rule;
import org.wei86609.osmanthus.node.Split;
import org.wei86609.osmanthus.node.Start;
import org.wei86609.osmanthus.node.executor.ruleset.GeneralRuleSetExecutor;
import org.wei86609.osmanthus.node.ruleset.CardRuleSet;
import org.wei86609.osmanthus.node.ruleset.GeneralRuleSet;

import com.thoughtworks.xstream.XStream;

public class FlowFileTranslator implements NodeTranslator<Flow>{

    private String flowFolder;

    private String flowFolderName="flow";

    private String flowFileExtension=".fl";

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

    public List<Flow> getNodes()throws Exception{
        List<Flow> flowList=new ArrayList<Flow>();
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
                flowList.add(flow);
            }
        }
        return flowList;
    }

    private Flow loadFlowFromFile(File xmlfile) throws FileNotFoundException{
        XStream xs = new XStream();
        xs.processAnnotations(new Class[]{Flow.class,Start.class,End.class,GeneralRuleSet.class,Rule.class,Split.class,Constraint.class,CardRuleSet.class});
        return (Flow)xs.fromXML(new FileInputStream(xmlfile));
    }

    public Flow getNode(String flowId) throws Exception {
        List<Flow> flows=getNodes();
        for(Flow flow:flows){
            if(flow!=null && flow.getId()!=null && flow.getId().equals(flowId)){
                return flow;
            }
        }
        return null;
    }

}

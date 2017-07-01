package org.wei86609.osmanthus.translator;

import java.util.List;

import org.wei86609.osmanthus.node.Node;
/**
 * 规则解释器
 * @author wangwei19
 *
 */
public interface NodeTranslator<OUTPUT extends Node>{

    public List<OUTPUT> getNodes()throws Exception;

    public OUTPUT getNode(String group)throws Exception;
}

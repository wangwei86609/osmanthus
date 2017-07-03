package org.wei86609.osmanthus.translator;

import java.util.Map;

import org.wei86609.osmanthus.node.Node;
/**
 * 规则解释器
 * @author wangwei19
 *
 */
public interface NodeTranslator<KEY,OUTPUT extends Node>{

    public Map<KEY,OUTPUT> getNodes()throws Exception;

    public OUTPUT getNode(String group)throws Exception;
}

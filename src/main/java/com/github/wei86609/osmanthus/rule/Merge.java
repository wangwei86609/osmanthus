package com.github.wei86609.osmanthus.rule;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.github.wei86609.osmanthus.event.Event;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("merge")
public class Merge extends Rule{

    private final ConcurrentHashMap<String, Event> allEvents = new ConcurrentHashMap<String, Event>();

    private int lineCnt;

    public synchronized int getLineCnt() {
        return lineCnt;
    }

    public void setLineCnt(int lineCnt) {
        this.lineCnt = lineCnt;
    }

    public synchronized boolean canMerge(Event event){
        lineCnt=lineCnt-1;
        if(lineCnt>0){
            allEvents.put(Thread.currentThread().getName(), event);
            return false;
        }else{
            Set<Entry<String,Event>> entries=allEvents.entrySet();
            for(Entry<String,Event> entry:entries){
                event.getVars().putAll(entry.getValue().getVars());
            }
            allEvents.clear();
            return true;
        }
    }

    @Override
    public TYPE getType() {
        return TYPE.MERGE;
    }

}

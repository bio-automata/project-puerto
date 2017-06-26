package model.system;

import model.events.Event;
import java.util.ArrayList;

/**
  FutureEventList

  This class is an abstraction of a ordered 
  event sequence. 

  Each event is inserted in chronologic order, 
  thus the immediate event is positioned in
  first cell of the list 

 */
public class FutureEventList {
    private ArrayList<Event> futureEventList;

    public FutureEventList(){
        this.futureEventList = new ArrayList();
    }

    public Event getEvent(){
    	//System.out.println(this.futureEventList.size());
    	Event event = this.futureEventList.get(0);
    	this.futureEventList.remove(0);
        return event;
    }

    
    
    public void addEvent(Event term){
    	/*
    	Collections.sort(this.futureEventList, new Comparator<Event>() {
            @Override
            public int compare(EventNotice eventNotice, EventNotice t1) {
                if(eventNotice.getHoraFim()>t1.getHoraFim()) return 1;
                if(eventNotice.getHoraFim()<t1.getHoraFim()) return -1;

                return 0;
            }
    		});
    	*/
    	
    	if(this.futureEventList.size()==0){
    		this.futureEventList.add(term);
    	}
    	else{
    		int li = 0;
    		int ls = this.futureEventList.size();
    		int m;
    		
    		while(true){
    			m =  Math.round((ls+li)/2);
    			if(li==ls || Math.abs(ls-li)<=1){
    				if(term.getOccurrenceTime()<=this.futureEventList.get(m).getOccurrenceTime()){
    					this.futureEventList.add(m,term);
    				}
    				else if(term.getOccurrenceTime()>this.futureEventList.get(m).getOccurrenceTime()){
    					this.futureEventList.add(m+1,term);
    				}
    				
    				return;
    			}
    			if(term.getOccurrenceTime()<=this.futureEventList.get(m).getOccurrenceTime()){
    				ls = m;
    			}
    			else if(term.getOccurrenceTime()>this.futureEventList.get(m).getOccurrenceTime()){
    				li = m;
    			}
    		}
    	}
    }
}




package simulator.control;

import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;


public class Controller {

    private TrafficSimulator _sim;
    private Factory<Event> _eventsFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
        if(sim == null){
            throw new IllegalArgumentException("The traffic simulator is null");
        }
        this._sim = sim;

        if(eventsFactory == null){
            throw new IllegalArgumentException("The events factory is null");
        }
        this._eventsFactory = eventsFactory;
    }

    public void loadEvents(InputStream in){
        JSONObject jo = new JSONObject(new JSONTokener(in));
        for(Object e : jo.getJSONArray("events")){
        	JSONObject jojo = new JSONObject(e.toString());
            this._sim.addEvent(this._eventsFactory.createInstance(jojo));
        }
    }

    public void run(int n) {
    	for (int i = 0; i < n;i++) {
    		_sim.advance();
    	}
    	
    }
    
    public void run(int n, OutputStream out){
        PrintStream p = new PrintStream(out);;
        p.println( "{ states: [");
        for(int i = 0; i < n-1; i++){
            this._sim.advance();
            p.println (this._sim.report());
            p.println(",");
        }
        if(n>0){
            this._sim.advance();
            p.println(this._sim.report());
        }
        p.println( "]");
        p.println("}");
    }

    public void addObserver(TrafficSimObserver o){
        _sim.addObserver(o);
    }
    
    public void removeObserver(TrafficSimObserver o){
        _sim.removeObserver(o);
    }
    
    public void addEvent(Event e){
        _sim.addEvent(e);
    }

    public void reset(){
        this._sim.reset();
    }
}


package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int timeSlot;

	MostCrowdedStrategy(int timeSlot){
    	this.timeSlot = timeSlot;
    }
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		
		if (roads.isEmpty())
    		return -1;
		if (currGreen != -1 && currTime - lastSwitchingTime < timeSlot)
			return currGreen;
		
		int largest = 0;
		int indexoflargest = currGreen + 1;
		int index = currGreen + 1;
		
		for (int i = 0; i <= roads.size(); i++) //checks all the roads
		{
			index = currGreen + 1 + i;
			
			//Check index doesn't go out of list
			if (index >= roads.size())
				index = index - roads.size();
			
			if (qs.get(index).size() > largest)
			{
				largest = qs.get(index).size();
				indexoflargest = index;
			}
			
		}
		return indexoflargest;
	}

}

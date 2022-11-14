import java.util.EmptyStackException;

public class PatientQueue{
	// array stores 10 elements, as index 0 is not used
	private static int DEFAULT_SIZE = 11;
	private int size = 0;
	private Patient[] patientList;

	public PatientQueue(){
		patientList = new Patient[DEFAULT_SIZE];
	}
	
	private void append(Patient patient) {
		if (size >= patientList.length - 1) {
			this.increaseSize();
		}
		patientList[size] = patient;
	}
	
	private void increaseSize() {
		int newSize = patientList.length * 2;
		Patient[] temp = new Patient[newSize];
		for(int i = 1; i < this.size; i++) {
			temp[i] = patientList[i];
		}
		patientList = temp;
	}
	
	private void bubbleUp() {
		//System.out.println(patientList.length + " LIST SIZE");
		//System.out.println(size + " ELEMENT COUNT");
		//System.out.println(size + " SIZE");
		int curIndex = size;
		int parentIndex = (int) (curIndex/2);
		Patient temp;
		
		while (true) {
			//System.out.println(curIndex + " CURINDEX");
			Patient child = patientList[curIndex];
			Patient parent = patientList[parentIndex];
			//System.out.println(child + " CHILD");
			//System.out.println(parent + " PARENT");
			//System.out.println(this.toString() + " WHOLE LIST");
			if (parentIndex > 0 && parent != null && child != null) {
				//System.out.println("FIRST IF");
				if (child.priority < parent.priority) {
					//System.out.println("IF");
					temp = parent;
					patientList[parentIndex] = patientList[curIndex];
					patientList[curIndex] = temp;
					curIndex = parentIndex;
				}
				else if (child.priority == parent.priority) {
					//System.out.println("ELSE IF");
					if (child.name.compareToIgnoreCase(parent.name) < 0) {
						temp = parent;
						patientList[parentIndex] = patientList[curIndex];
						patientList[curIndex] = temp;
						curIndex = parentIndex;
					}
					else {
						//System.out.println("END");
						break;
					}
				}
				else {
					break;
					//System.out.println(child + " CHILD");
					//System.out.println(parent + " PARENT");
				}
			}
			else {
				//System.out.println("FIRST ELSE BREAK");
				//System.out.println(patientList[size-1] + " LIST END");
				//System.out.println(patientList.length + " LIST LENGTH");
				break;
			}
			//System.out.println(parentIndex);
			parentIndex = (int) (curIndex/2);
		}
	}
	
	private void bubbleDown() {
		//System.out.println(this.toString() + " INITIAL");
		int curIndex = size;
		Patient childOne;
		Patient childTwo;
		//System.out.println("INFRONT: " + patientList[1]);
		
		try {
			patientList[1] = patientList[curIndex];
			curIndex = 1;
			while (true) {
				if (patientList[curIndex] == null) {
					break;
				}
				//System.out.println(this.toString() + " WHILE LOOP LIST");
				int childIndexOne = (curIndex * 2);
				int childIndexTwo = ((curIndex * 2)+1);
				try {
					childOne = patientList[childIndexOne];
				}
				catch (Exception e) {
					childOne = null;
				}
				try {
					childTwo = patientList[childIndexTwo];
				}
				catch (Exception e) {
					childTwo = null;
				}

				Patient temp = patientList[curIndex];
				//System.out.println(this.toString() + " WHILE LOOP PRINT");
				//System.out.println("CUR: " + temp + " INDEX: " + curIndex);
				//System.out.println("LCHILD: " + childOne + " INDEX: " + childIndexOne);
				//System.out.println("RCHILD: " + childTwo + " INDEX: " + childIndexTwo);
				
				if (childOne != null && childTwo != null) {
					//System.out.println("ONE");
					if (childOne.priority < childTwo.priority) {
						//System.out.println("CHILD ONE SMALLER");
						patientList[curIndex] = childOne;
						patientList[childIndexOne] = temp;
						curIndex = childIndexOne;
					}
					else if (childOne.priority == childTwo.priority) {
						if (childOne.name.compareToIgnoreCase(childTwo.name) <= 0) {
							patientList[curIndex] = childOne;
							patientList[childIndexOne] = temp;
							curIndex = childIndexOne;
						}
						else {
							patientList[curIndex] = childTwo;
							patientList[childIndexTwo] = temp;
							curIndex = childIndexTwo;
						}
					}
					else {
						//System.out.println("CHILD TWO SMALLER");
						patientList[curIndex] = childTwo;
						patientList[childIndexTwo] = temp;
						curIndex = childIndexTwo;
					}
					
				}
				/*
				else if (childTwo == null) {
					System.out.println("FOUR");
					patientList[curIndex] = childOne;
					patientList[childIndexOne] = temp;
					curIndex = childIndexOne;
				}
				else if (childOne == null) {
					System.out.println("THREE");
					patientList[curIndex] = childTwo;
					patientList[childIndexTwo] = temp;
					curIndex = childIndexTwo;
				}
				*/
				else {
					//System.out.println("Break");
					break;
				}
			}
		}
		catch(Exception e) {
			//System.out.println(e + " PRINT EXCEPTION BUBBLE DOWN");
		}
	}
	
	public void enqueue(String name, int priority) {
		size += 1;
		/*
		if (size >= DEFAULT_SIZE) {
			this.increaseSize();
		}
		*/
		Patient patientToAdd = new Patient(name, priority);
		//System.out.println(this.toString() + " INTITIAL");
		this.append(patientToAdd);
		//System.out.println(this.toString() + " After Append");
		this.bubbleUp();
		//System.out.println(this.toString() + " After Bubble");
	}
	
	public void enqueue(Patient patient) {
		size += 1;
		this.append(patient);
		this.bubbleUp();
		
	}
	
	public String dequeue() {
		String retString;
		if (patientList[1] != null) {
		retString = patientList[1].toString();
		}
		else {
			retString = null;
		}
		
		if (size == 0) {
			retString = null;
		}
		//System.out.println(this.toString() + " BEFORE BUBBLE");
		this.bubbleDown();
		//System.out.println(this.toString() + " AFTER BUBBLE");
		//System.out.println("____________________________");
		try {
			//System.out.println(patientList[size] + " ENTRY AT END");
			patientList[size] = null;
		}
		catch(Exception e) {
			//System.out.println(e + " DEQUEUE EXCEPTION");
		}
		this.minusSize();
		return retString;
	}
	
	public int peekPriority(){
		if (patientList[1] != null) {
			return patientList[1].priority;
		}
		throw new EmptyStackException();
		
	}
	
	public void changePriority(String name, int newPriority) {
		
	}
	
	public boolean isEmpty() {
		return false;
	}
	
	public int size() {
		return size;
	}
	
	private void minusSize() {
		if (size < 1) {
			size = 0;
		}
		else {
			size -= 1;
		}
	}
	
	public void clear() {
		
	}
	
	public String toString() {
		String retString = "{";
		//System.out.println(patientList[9]);
		try {
			if (size != 0) {
				retString += patientList[1].name + "(" + patientList[1].priority + ")";
				for (int i = 2; i < this.size() + 1; i++) {
					//System.out.println("Index: " + i + "Patient: " +patientList[i]);
					retString += (", " + patientList[i].name + "(" + patientList[i].priority + ")");
				}
			}
		}
		catch(Exception e) {
			//System.out.println(e);
			//System.out.println("EXCPETION PRINT");
		}
		retString += "}";
		return retString;
	}
	
}

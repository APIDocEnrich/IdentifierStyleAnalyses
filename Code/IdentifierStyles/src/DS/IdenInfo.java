package DS;

public class IdenInfo {
	
	String project;
	String  type;
	int lengthAtchar;
	int lengthAtterm;
	float importance;
	float entropy;
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	 
	public IdenInfo(String project, String type, int lengthAtchar, int lengthAtterm, float importance,float entropy) {
		super();
		this.project = project;
		this.type = type;
		this.lengthAtchar = lengthAtchar;
		this.lengthAtterm = lengthAtterm;
		this.importance = importance;
		this.entropy = entropy;
	}
	public float getEntropy() {
		return entropy;
	}
	public void setEntropy(float entropy) {
		this.entropy = entropy;
	}
	public float getImportance() {
		return importance;
	}
	public void setImportance(float importance) {
		this.importance = importance;
	}
	public int getLengthAtchar() {
		return lengthAtchar;
	}
	public void setLengthAtchar(int lengthAtchar) {
		this.lengthAtchar = lengthAtchar;
	}
	public int getLengthAtterm() {
		return lengthAtterm;
	}
	public void setLengthAtterm(int lengthAtterm) {
		this.lengthAtterm = lengthAtterm;
	}
	 
	
	

}

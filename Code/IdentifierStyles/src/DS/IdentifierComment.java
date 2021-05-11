package DS;

public class IdentifierComment {

	
	String granularity;
	String idname;
	String idtype;
	String iddefault;
	String comment;
	
	
	public String getGranularity() {
		return granularity;
	}
	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}
	public String getIdname() {
		return idname;
	}
	public void setIdname(String idname) {
		this.idname = idname;
	}
	public String getIdtype() {
		return idtype;
	}
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}
	public String getIddefault() {
		return iddefault;
	}
	public void setIddefault(String iddefault) {
		this.iddefault = iddefault;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public IdentifierComment(String granularity, String idname, String idtype, String iddefault, String comment) {
		super();
		this.granularity = granularity;
		this.idname = idname;
		this.idtype = idtype;
		this.iddefault = iddefault;
		this.comment = comment;
	}
	
	public String toString()
	{

		if(this.iddefault.contains(","))
			this.iddefault=this.iddefault.replace(",", " ");
		if(this.iddefault.contains("\n"))
			this.iddefault=this.iddefault.replaceAll("\r|\n", " ");
		
		if(this.comment.contains(","))
			this.comment=this.comment.replace(",", " ");
		if(this.comment.contains("\n"))
			this.comment=this.comment.replaceAll("\r|\n", " ");
		
		if(this.idtype.contains(","))
			this.idtype=this.idtype.replace(",", " ");
		if(this.idtype.contains("\n"))
			this.idtype=this.idtype.replaceAll("\r|\n", " ");
		
		if(this.idname.contains(","))
			this.idname=this.idname.replace(",", " ");
		if(this.idname.contains("\n"))
			this.idname=this.idname.replaceAll("\r|\n", " ");
		
		if(this.idtype.isEmpty())
			this.idtype=" ";
		if(this.iddefault.isEmpty())
			this.iddefault=" ";
		if(this.comment.isEmpty())
			this.comment=" ";
		if(this.idname.isEmpty())
			this.idname=" ";
		
		
		String result=this.granularity+","+this.idname+","+this.idtype+","+this.iddefault+","+this.comment;
		return result;
		
		
	}
	
}

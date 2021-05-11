package ParseInfo;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import DS.ClassDS;
import DS.IdentifierDS;
import DS.MethodDS;

public class ClassCollector extends VoidVisitorAdapter<Vector<ClassDS>> {
   
	@Override
    public void visit(ClassOrInterfaceDeclaration cORid, Vector<ClassDS> classNameSet) 
    {
//        classNameSet.add(cORid.getNameAsString()); 
        
        Vector<MethodDS> methodlist=new Vector<MethodDS>();
        Vector<MethodDS> constructorlist=new Vector<MethodDS>();
        List<MethodDeclaration> methods=cORid.getMethods();
        
        
        
        List<ConstructorDeclaration> constructors=cORid.getConstructors();
        for(ConstructorDeclaration method:constructors)
        {
           Vector<IdentifierDS> paralist=new Vector<IdentifierDS>();
        	
        	String location=method.getBegin().toString();
        	int beginindex=Integer.parseInt(location.substring(location.indexOf("line")+4, location.indexOf(",")).trim());
        	
        	List<Parameter> paras=method.getParameters();
        	if(!paras.isEmpty())
        	{
	        	for(Parameter para:paras)
	        	{
//	        		System.out.println(para.getNameAsString()+" "+para.getType()); 
	        		IdentifierDS onepara=new IdentifierDS(cORid.getNameAsString(),method.getNameAsString(),para.getNameAsString(),para.getType().toString(),"",beginindex);
	        		paralist.add(onepara);
	        	}
        	}
        	  
        	 
        	String outcomment=method.getComment().toString();
        	String incomment=method.getAllContainedComments().toString();
        	
        	location=method.getEnd().toString();
        	int endindex=Integer.parseInt(location.substring(location.indexOf("line")+4, location.indexOf(",")).trim());
        	String body=method.getBody().toString();
        	
        	MethodDS newmethod=new MethodDS(method.getNameAsString(),paralist,"",outcomment,incomment,beginindex,endindex,body);
        	
        	constructorlist.add(newmethod);
        }
        
       
        for(MethodDeclaration method:methods)
        {
//        	System.out.println(method.getNameAsString());  
        	
        	Vector<IdentifierDS> paralist=new Vector<IdentifierDS>();
        	
        	String location=method.getBegin().toString();
        	int beginindex=Integer.parseInt(location.substring(location.indexOf("line")+4, location.indexOf(",")).trim());
        	
        	List<Parameter> paras=method.getParameters();
        	if(!paras.isEmpty())
        	{
	        	for(Parameter para:paras)
	        	{
//	        		System.out.println(para.getNameAsString()+" "+para.getType()); 
	        		IdentifierDS onepara=new IdentifierDS(cORid.getNameAsString(),method.getNameAsString(),para.getNameAsString(),para.getType().toString(),"",beginindex);
	        		paralist.add(onepara);
	        	}
        	}
        	 
        	String returntype=method.getType().toString();
        	String outcomment=method.getComment().toString();
        	String incomment=method.getAllContainedComments().toString();
        	
        	location=method.getEnd().toString();
        	int endindex=Integer.parseInt(location.substring(location.indexOf("line")+4, location.indexOf(",")).trim());
        	String body=method.getBody().toString();
        	
        	MethodDS newmethod=new MethodDS(method.getNameAsString(),paralist,returntype,outcomment,incomment,beginindex,endindex,body);
        	
        	methodlist.add(newmethod);
        	
//        	System.err.println(method.getType()); 	
//        	System.err.println(method.getComment().toString());          	 
//        	System.err.println(method.getAllContainedComments().toString());         	
//        	System.err.println(method.getRange()+"  "+method.getBegin()+"  "+method.getEnd());          	
//        	System.err.println(method.getBody().toString());                    
        }
       
        
        Vector<IdentifierDS> fieldlist=new Vector<IdentifierDS>();
        List<FieldDeclaration> fields=cORid.getFields();
        for(FieldDeclaration field:fields)
        {
        	
        	
        	String location=field.getBegin().toString();
        	int beginindex=Integer.parseInt(location.substring(location.indexOf("line")+4, location.indexOf(",")).trim());
        	
        	
        	String type=field.getElementType().toString();
        	
        	NodeList<VariableDeclarator> var=field.getVariables();
        	for(int i=0;i<var.size();i++)
        	{
        		
        		VariableDeclarator onevar=var.get(i);
        		String name=onevar.getName().toString();
        		
        		String defaulted="";
        		Optional<Expression> defaultvalue=onevar.getInitializer();
        		if(defaultvalue.isPresent())
        		{
        			defaulted=defaultvalue.get().toString();
        			
        		}
        		
        		IdentifierDS oneidentifier=new IdentifierDS(cORid.getNameAsString(),"",name,type,defaulted,beginindex);
    			fieldlist.add(oneidentifier);
//    			System.out.println(oneidentifier.toString());
    			
        	}
    			
    			
        }
        
        
        
        String location=cORid.getBegin().toString();
    	int index=Integer.parseInt(location.substring(location.indexOf("line")+4, location.indexOf(",")).trim());
//        ClassDS oneclass=new ClassDS(cORid.getNameAsString(),methodlist,fieldlist,constructorlist,index);
    	
        
        
    	String endlocation=cORid.getEnd().toString();
    	int endindex=Integer.parseInt(endlocation.substring(endlocation.indexOf("line")+4, endlocation.indexOf(",")).trim());
    	int finalindex=index*100000+endindex;
        ClassDS oneclass=new ClassDS(cORid.getNameAsString(),methodlist,fieldlist,constructorlist,finalindex);

    	
    	
        classNameSet.add(oneclass);
        super.visit(cORid, classNameSet);
    }
 
}

package ParseInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

import DS.ClassDS;
import DS.IdentifierDS;
import DS.MethodDS;

public class ParseJavaContent {
	
	 
	 public static void ListAndParseJavaFiles() throws Exception
	 {
			BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\repository_state.csv"));
			String line="";
			while((line=br.readLine())!=null)
			{
				int index=line.indexOf("github.com/");
				String url="https://"+line.substring(index, line.lastIndexOf(".git"));
				
				String foldername="";
				String[] split=url.split("/");
				if(split.length>4){
					foldername=split[3]+"_"+split[4];
				}
				else{
					foldername=split[3]+"_"+split[3];
				}
				
				File f=new File("D:\\project\\IdentifierStyle\\data\\GitProject\\"+foldername+"\\");
				if(f.exists())
				{
					System.out.println(foldername);
					recordJavaFile(f,"D:\\project\\IdentifierStyle\\data\\JavaFileIndex\\"+foldername+".txt");
					ParseAllContent(foldername,"D:\\project\\IdentifierStyle\\data\\JavaFileIndex\\"+foldername+".txt");
				}
				
			}
			br.close();
	 }
	
	public static void recordJavaFile(File current,String staticfile) throws Exception
	 {
	    	File[] allfile=current.listFiles();
	    	for(int i=0;i<allfile.length;i++)
	    	{
	    		if(allfile[i].isFile())
	    		{
	    			BufferedWriter bw=new BufferedWriter(new FileWriter(staticfile,true));
		    		String name=allfile[i].getName();
		    		if(name.endsWith(".java"))
		    		{
		    			bw.write(allfile[i].getPath());
		    			bw.newLine();
		    			 
		    		}
		    		bw.close();
	    		}
	    		else if(allfile[i].isDirectory())
	    		{
	    			recordJavaFile(allfile[i],staticfile);
	    		}
	    	}
	   }
	 
	
	
	public static void ParseAllContent(String outputname,String projectinfo) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\data\\IdentifierList\\"+outputname+".csv"));
		BufferedReader br=new BufferedReader(new FileReader(projectinfo));
		String line="";
		while((line=br.readLine())!=null)
		{
//			System.out.println(line);
			BufferedWriter bws=new BufferedWriter(new FileWriter("C:\\Users\\Desktop\\temp.txt"));
			if(!line.endsWith("XLargeTest.java"))
			{
				bws.write(line);
				bws.newLine();
				bws.close();
				String s=ObtainIdentifier(line);
				bw.write(s);
			}
			
		}
		br.close();
		bw.close();
		
	}
		
	
	public static String ObtainIdentifier(String javafilepath) throws Exception
	{
		Vector<IdentifierDS> packages=new Vector<IdentifierDS>();
		Vector<IdentifierDS> types=new Vector<IdentifierDS>(); 
		Vector<IdentifierDS> methods=new Vector<IdentifierDS>();
		Vector<IdentifierDS> fields=new Vector<IdentifierDS>(); 
		Vector<IdentifierDS> variables=new Vector<IdentifierDS>(); 
        
		CompilationUnit cu =null;
		
//		System.out.println(javafilepath);
		try {
             cu = JavaParser.parse(new File(javafilepath));
            
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
			BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\data\\JavaParserCannotParse.txt",true));
            bw.write(javafilepath);
            bw.newLine();
            bw.close();
		}
        
        String packagename="";
        try {
        	Optional<PackageDeclaration> packagename1=cu.getPackageDeclaration();
        	if(packagename1.isPresent())
            {
            	
            	packagename=packagename1.get().toString().trim();
            	if(packagename.startsWith("/*"))
            		packagename=packagename.substring(packagename.indexOf("*/")+2, packagename.length()).trim();
            	if(packagename.startsWith("//"))
            		packagename=packagename.substring(packagename.indexOf("package "),packagename.length());
            	if(packagename.startsWith("package "))
            		packagename=packagename.substring(packagename.indexOf(" ")+1,packagename.length());
                if(packagename.endsWith(";"))
                	packagename=packagename.substring(0,packagename.length()-1);
            }
             
        }
        catch(Exception e)
		{
			System.err.println(e.toString());
		}
        
        
        
       
        IdentifierDS newpackage=new IdentifierDS("","",packagename,"","",1);
        packages.add(newpackage);
         
        
        Hashtable<String,Integer> variableSet =new Hashtable<String,Integer>();	    	
        VoidVisitor<Hashtable<String, Integer>> VariableCollector = new VariableCollector();
        try {
        VariableCollector.visit(cu, variableSet);
        }
        catch(Exception e)
		{
			System.err.println(e.toString());
		}
      
        
    	Vector<ClassDS> classdetails=new Vector<ClassDS>();
        VoidVisitor<Vector<ClassDS>> classNameCollector = new ClassCollector();
        try {
        	classNameCollector.visit(cu, classdetails);
        }
        catch(Exception e)
		{
			System.err.println(e.toString());
		}

        
        
        for(ClassDS one :classdetails)
        {
          	IdentifierDS newclass=new IdentifierDS(one.getClassname(),"",one.getClassname(),"class","",one.getIndex());
          	types.add(newclass);
          	
    		Vector<MethodDS> methodlist=one.getMethodlist();
          	for(MethodDS onemethod:methodlist)
          	{
          		IdentifierDS newmethod=new IdentifierDS(one.getClassname(),onemethod.getMethodname(),onemethod.getMethodname(),onemethod.getReturntype(),"",onemethod.getBeginindex());
          		methods.add(newmethod);
          		
          		Vector<IdentifierDS> parameters=onemethod.getParameters();
          		for(IdentifierDS oneid:parameters)
          		{
	        			variables.add(oneid);
          		}
          		
          	}
          	
 
          	
          	Vector<IdentifierDS> fieldlist=one.getFieldlist();
          	fields.addAll(fieldlist);
          	
        }
        
        
        Set<String> keyset=variableSet.keySet();
        for(String onekey:keyset)
        {
        	int onevalue=variableSet.get(onekey);
        	String methodpar="";
        	String classpar="";
        	for(ClassDS one :classdetails)
            {
              	              	
        		Vector<MethodDS> methodlist=one.getMethodlist();
              	for(MethodDS onemethod:methodlist)
              	{
              		if(onevalue>=onemethod.getBeginindex()&&onevalue<=onemethod.getEndindex())
              		{
              			methodpar=onemethod.getMethodname();
              			classpar=one.getClassname();
              			break;
              		}
              	}
            }
        	
        	
        	if(onekey.contains("="))
        	{
        		String front=onekey.substring(0, onekey.indexOf("=")).trim();
        		String end=onekey.substring(onekey.indexOf("=")+1, onekey.length()).trim();
        		String name=front.substring(front.lastIndexOf(" ")+1, front.length()).trim();
        		String type="";
        		
        		if(front.contains(" "))
        		{
        			front=front.substring(0, front.lastIndexOf(" "));
        			if(front.contains(" "))
        			{
        				type=front.substring(front.lastIndexOf(" ")+1,front.length()).trim();
        			}
        			else type=front;
        		}
        		else
        			type=front;
        		
        		type=type.trim();
        		IdentifierDS oneid=new IdentifierDS(classpar,methodpar,name,type,end,onevalue);
        		 
        		variables.add(oneid);
        		
        				
        	}
        	else
        	{
        		String name=onekey.substring(onekey.lastIndexOf(" ")+1, onekey.length()).trim();
        		onekey=onekey.substring(0, onekey.lastIndexOf(" ")).trim();
        		String type="";
        		if(onekey.contains(" "))
        			type=onekey.substring(onekey.lastIndexOf(" ")+1, onekey.length()).trim();
        		else
        			type=onekey;
        		
        		type=type.trim();
        		
        		IdentifierDS oneid=new IdentifierDS(classpar,methodpar,name,type,"",onevalue);
        		
        		variables.add(oneid);
        			 
        	}      	
        }
        
        
        StringBuilder sb=new StringBuilder();
        for(IdentifierDS onepackage:packages)
        {
        	sb.append("1,"+packagename+","+onepackage.toString()+"\n");
        }
        for(IdentifierDS onetype:types)
        {
        	sb.append("2,"+packagename+","+onetype.toString()+"\n");
        }
        for(IdentifierDS onemethod:methods)
        {
        	sb.append("3,"+packagename+","+onemethod.toString()+"\n");
        }
        for(IdentifierDS onefield:fields)
        {
        	sb.append("4,"+packagename+","+onefield.toString()+"\n");
        }
        for(IdentifierDS onevariable:variables)
        {
        	sb.append("5,"+packagename+","+onevariable.toString()+"\n");
        }
        
        
        return sb.toString();
        
    }
 

	
}

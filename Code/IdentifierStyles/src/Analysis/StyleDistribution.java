package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import uk.ac.open.crc.intt.IdentifierNameTokeniser;
import uk.ac.open.crc.intt.IdentifierNameTokeniserFactory;


public class StyleDistribution {
	

		
	public static void AnalyzeUnderscore() throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\distribution\\UnderscoreAnalysis.csv"));
		BufferedReader br1=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\FinalProjects.txt"));
		String project="";
		while((project=br1.readLine())!=null)
		{
			int count1_1=0, count1_2=0, count2_1=0, count2_2=0, count3_1=0, count3_2=0, count4_1=0, count4_2=0, count5_1=0, count5_2=0;
			BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\distribution\\AllDis\\"+project+"_underscore.txt"));
			String line="";
			while((line=br.readLine())!=null)
			{
				int id=Integer.parseInt(line.substring(0, line.indexOf(",")));
				line=line.substring(line.indexOf(",")+1, line.length());
				if(CheckAllUnderscoreSeparated(line.trim()))
				{
					if(id==1)
						count1_1++;
					else if(id==2)
						count2_1++;
					else if(id==3)
						count3_1++;
					else if(id==4)
						count4_1++;
					else if(id==5)
						count5_1++;
					else
						System.err.println(line);
				}
				else
				{
					if(id==1)
						count1_2++;
					else if(id==2)
						count2_2++;
					else if(id==3)
						count3_2++;
					else if(id==4)
						count4_2++;
					else if(id==5)
						count5_2++;
					else
						System.err.println(line);
				}
			}
			br.close();
			bw.write(project+",package,"+count1_1+","+count1_2+","+(count1_1+count1_2)+",");
			bw.newLine();
			bw.write(project+",type,"+count2_1+","+count2_2+","+(count2_1+count2_2)+",");
			bw.newLine();
			bw.write(project+",method,"+count3_1+","+count3_2+","+(count3_1+count3_2)+",");
			bw.newLine();
			bw.write(project+",field,"+count4_1+","+count4_2+","+(count4_1+count4_2)+",");
			bw.newLine();
			bw.write(project+",variable,"+count5_1+","+count5_2+","+(count5_1+count5_2)+",");
			bw.newLine();
			
		}
		br1.close();
		bw.close();
	}
	
	public static void AnalyzeCamelCase() throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\distribution\\CamelCaseAnalysis.csv"));
		BufferedReader br1=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\FinalProjects.txt"));
		String project="";
		while((project=br1.readLine())!=null)
		{
			int count1_1=0, count1_2=0, count2_1=0, count2_2=0, count3_1=0, count3_2=0, count4_1=0, count4_2=0, count5_1=0, count5_2=0;
			BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\distribution\\AllDis\\"+project+"_camel.txt"));
			String line="";
			while((line=br.readLine())!=null)
			{
				int id=Integer.parseInt(line.substring(0, line.indexOf(",")));
				line=line.substring(line.indexOf(",")+1, line.length());
				if(Character.isLowerCase(line.trim().charAt(0)))
				{
					if(id==1)
						count1_1++;
					else if(id==2)
						count2_1++;
					else if(id==3)
						count3_1++;
					else if(id==4)
						count4_1++;
					else if(id==5)
						count5_1++;
					else
						System.err.println(line);
				}
				else
				{
					if(id==1)
						count1_2++;
					else if(id==2)
						count2_2++;
					else if(id==3)
						count3_2++;
					else if(id==4)
						count4_2++;
					else if(id==5)
						count5_2++;
					else
						System.err.println(line);
				}
			}
			br.close();
			bw.write(project+",package,"+count1_1+","+count1_2+","+(count1_1+count1_2)+",");
			bw.newLine();
			bw.write(project+",type,"+count2_1+","+count2_2+","+(count2_1+count2_2)+",");
			bw.newLine();
			bw.write(project+",method,"+count3_1+","+count3_2+","+(count3_1+count3_2)+",");
			bw.newLine();
			bw.write(project+",field,"+count4_1+","+count4_2+","+(count4_1+count4_2)+",");
			bw.newLine();
			bw.write(project+",variable,"+count5_1+","+count5_2+","+(count5_1+count5_2)+",");
			bw.newLine();
			
		}
		br1.close();
		bw.close();
	}
	
	public static boolean CheckAllUnderscoreSeparated(String s)
	{
		if(s.startsWith("_"))
			s=s.substring(1, s.length());
		if(s.endsWith("_"))
			s=s.substring(0, s.length()-1);
		LinkedList<String> v=new LinkedList<String>();
		IdentifierNameTokeniserFactory a=new IdentifierNameTokeniserFactory();
		IdentifierNameTokeniser b=a.create();
		try {
			String temp[]=b.tokenise(s);
			for(int i=0;i<temp.length;i++)
				v.add(temp[i]);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		int underscorecount=0;
		for(int i=0;i<s.length();i++)
		{
			if(s.charAt(i)=='_')
				underscorecount++;
		}
		if(underscorecount==(v.size()-1))
			return true;
		else
			return false;
	}
	
	
	public static void CalculatePercentage() throws Exception
	{
		
        BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\distribution\\camel_underscore.csv"));
		BufferedReader br1=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\FinalProjects.txt"));
		String project="";
		while((project=br1.readLine())!=null)
		{	
			HashSet<String> all11=new HashSet<String>();
			ArrayList<String> all2=new ArrayList<String>();
			ArrayList<String> all3=new ArrayList<String>();
			ArrayList<String> all4=new ArrayList<String>();
			ArrayList<String> all5=new ArrayList<String>();
						
			BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\IdentifierList\\"+project+".csv"));
			String line="";
			while((line=br.readLine())!=null)
			{
				if(line.startsWith("1,"))
					all11.add(line);
				else if(line.startsWith("2,"))
					all2.add(ExtractIdentifierName(line,project));
				else if(line.startsWith("3,"))
					all3.add(ExtractIdentifierName(line,project));
				else if(line.startsWith("4,"))
					all4.add(ExtractIdentifierName(line,project));
				else if(line.startsWith("5,"))
					all5.add(ExtractIdentifierName(line,project));
			}
			br.close();
			
			
			ArrayList<String> all1=new ArrayList<String>();
			
			for(String lines:all11)
			{
				String identifier=ExtractIdentifierName(lines,project);
				if(identifier.contains("."))
					identifier=identifier.substring(identifier.lastIndexOf(".")+1, identifier.length());
				if(!identifier.trim().isEmpty())
					all1.add(identifier);
			}
			
			String result1=CalculateNumber(all1,project,1);
			bw.write(project+",package,"+result1);
			bw.newLine();
			
			String result2=CalculateNumber(all2,project,2);
			bw.write(project+",type,"+result2);
			bw.newLine();
			
			String result3=CalculateNumber(all3,project,3);
			bw.write(project+",method,"+result3);
			bw.newLine();
			
			String result4=CalculateNumber(all4,project,4);
			bw.write(project+",field,"+result4);
			bw.newLine();
			
			String result5=CalculateNumber(all5,project,5);
			bw.write(project+",variable,"+result5);
			bw.newLine();
			
			 
			
		}
		bw.close();
		br1.close();
		
		
	}
	
	public static String ExtractIdentifierName(String line,String project) throws Exception
	{
		String result="";
		if(line.contains(","))
		{
			String[] parts=line.split(",");
			if(parts.length>=5)
			{
				result=parts[4];
			}
			else
			{
				System.err.println(line+" "+parts.length);
				
			}
		}
		return result;
	}
	
	public static String CalculateNumber(ArrayList<String> all,String project,int id) throws Exception
	{
		int underscore=0,camel=0,others=0;
		Vector<String> underv=new Vector<String>();
		Vector<String> camelv=new Vector<String>();
		Vector<String> othersv=new Vector<String>();
		
		for(String IdentifierName:all)
		{
			if(IdentifierName.contains("_"))
			{
				underscore++;
				underv.add(IdentifierName);
			}
			else
			{
				LinkedList<String> result=CamelCaseSplit(IdentifierName);
				if(result.size()>1)
				{
					camel++;
					camelv.add(IdentifierName);
					
				}
				else if(result.size()==1)
				{
					others++;
					othersv.add(IdentifierName);
				}
			}
	    }
		
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\distribution\\AllDis\\"+project+"_underscore.txt",true));
		for(String IdentifierName:underv)
		{
			bw.write(id+","+IdentifierName);
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\distribution\\AllDis\\"+project+"_camel.txt",true));
		for(String IdentifierName:camelv)
		{
			bw.write(id+","+IdentifierName);
			bw.newLine();
		}
		bw.close();
		
			
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\distribution\\AllDis\\"+project+"_others.txt",true));
		for(String IdentifierName:othersv)
		{
			bw.write(id+","+IdentifierName);
			bw.newLine();
		}
		bw.close();
		
		
		return underscore+","+camel+","+others+","+(underscore+camel+others);
		
	}
	
	public static LinkedList<String> CamelCaseSplit(String s) 
	{
		

		
		
		LinkedList<String> resultlist = new LinkedList<String>();
		if(s.isEmpty())
		{
			return resultlist;
		}
		StringBuilder word = new StringBuilder();
		char[] buf = s.toCharArray();
		boolean prevIsupper=false;
		for(int i=0;i<buf.length;i++)
		{
			char ch= buf[i];
			if(Character.isUpperCase(ch))
			{
				if(i==0)
				{
					word.append(ch);
				}
				else if(!prevIsupper)
				{
					resultlist.add(word.toString());
					word=new StringBuilder();
					word.append(ch);
				}else
				{
					word.append(ch);
				}
				prevIsupper=true;
			}
			else
			{
				word.append(ch);
				prevIsupper=false;
			}
		}
		if(word!=null&&word.length()>0)
		{
			resultlist.add(word.toString());
		}
		
		
		return resultlist;
	
	}
	

}

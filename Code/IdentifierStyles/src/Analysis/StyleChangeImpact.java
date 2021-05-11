package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

public class StyleChangeImpact {

	public static void main(String args[]) throws Exception
	{
		Precentagedistribution();
		
	}
	
	
	
	public static void UsageTimes() throws Exception
	{
        BufferedWriter bw1=new BufferedWriter(new FileWriter("C:\\Users\\Desktop\\package.csv"));
        BufferedWriter bw2=new BufferedWriter(new FileWriter("C:\\Users\\Desktop\\type.csv"));
        BufferedWriter bw3=new BufferedWriter(new FileWriter("C:\\Users\\Desktop\\method.csv"));
        BufferedWriter bw4=new BufferedWriter(new FileWriter("C:\\Users\\Desktop\\field.csv"));
        BufferedWriter bw5=new BufferedWriter(new FileWriter("C:\\Users\\Desktop\\variable.csv"));
        int cno=0;
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\log\\style_changed\\styled_changed_merge.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			System.out.println(cno);
			String project=line.substring(0, line.indexOf(","));
			line=line.substring(line.indexOf(",")+1,line.length());
			int cate=Integer.parseInt(line.substring(0,line.indexOf(",")));
			line=line.substring(line.indexOf(",")+1,line.length());
			int lineno=Integer.parseInt(line.substring(0,line.indexOf(",")));
			line=line.substring(line.indexOf(",")+1,line.length());
			String file=line.substring(0,line.indexOf(","));
			line=line.substring(line.indexOf(",")+1,line.length());
			String current=line.substring(0,line.indexOf(","));
			if(current.contains("<-"))
			{
				current=current.substring(0, current.indexOf("<-"));
			}
			
			Vector<String> filelist=new Vector<String>();
			BufferedReader read=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\JavaFileIndex\\"+project+".txt"));
			String onefile="";
			while((onefile=read.readLine())!=null)
			{
				 filelist.add(onefile);
			}
			read.close();
			
			if(cate==1)
			{
				int usagecount=0;
			    for(String onejavafile:filelist)
			    {
				   BufferedReader reader=new BufferedReader(new FileReader(onejavafile));
				   String statement="";
				   while((statement=reader.readLine())!=null)
				   {
					   statement=statement.trim();
					   if(statement.startsWith("import ")&&statement.endsWith(current+".*;"))
					   {
						   usagecount++;
					   }
				   }
				   reader.close();
			    }
			    bw1.write(project+","+lineno+","+file+","+current+","+usagecount);
			    bw1.newLine();
			}
			else if(cate==2)
			{
				int usagecount=0;
			    for(String onejavafile:filelist)
			    {
				   BufferedReader reader=new BufferedReader(new FileReader(onejavafile));
				   String statement="";
				   while((statement=reader.readLine())!=null)
				   {
					   statement=statement.replaceAll("[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]", " ");
					   if(statement.contains(" "+current+" "))
					   {
						   usagecount++;
					   }
					   
				   }
				   reader.close();
			    }
			    bw2.write(project+","+lineno+","+file+","+current.replace("\"", "")+","+(usagecount-1));
			    bw2.newLine();
			}
			else if(cate==3)
			{
				int usagecount=0;
			    for(String onejavafile:filelist)
			    {
				   BufferedReader reader=new BufferedReader(new FileReader(onejavafile));
				   String statement="";
				   while((statement=reader.readLine())!=null)
				   {
					   if(statement.contains("."+current+"(")||statement.contains(" "+current+"(")||statement.contains(current+"("))
					   {
						   usagecount++;
					   }
					   
				   }
				   reader.close();
			    }
			    bw3.write(project+","+lineno+","+file+","+current.replace("\"", "")+","+usagecount);
			    bw3.newLine();
			}
			else if(cate==4)
			{
				int usagecount=0;
			    for(String onejavafile:filelist)
			    {
				   BufferedReader reader=new BufferedReader(new FileReader(onejavafile));
				   String statement="";
				   while((statement=reader.readLine())!=null)
				   {
					   if(statement.contains("."+current)||statement.contains(" "+current)||statement.contains(current+" "))
					   {
						   usagecount++;
					   }
					   
				   }
				   reader.close();
			    }
			    bw4.write(project+","+lineno+","+file+","+current.replace("\"", "")+","+usagecount);
			    bw4.newLine();
			}
			else if(cate==5)
			{
				int usagecount=0;
			     
			    BufferedReader reader=new BufferedReader(new FileReader(file));
			    String statement="";
			    while((statement=reader.readLine())!=null)
			    {
			       statement=statement.replaceAll("[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]", " ");
				   if(statement.contains(" "+current+" ")||statement.contains(" "+current)||statement.contains(current+" "))
				   {
					   usagecount++;
				   }   
			    }
			   reader.close();
			     
			    bw5.write(project+","+lineno+","+file+","+current.replace("\"", "")+","+(usagecount-1));
			    bw5.newLine();
			}
			cno++;
		}
		br.close();
		bw1.close();
		bw2.close();
		bw3.close();
		bw4.close();
		bw5.close();
		
	}
	
	
	
	public static void FilePercentageDis() throws Exception
	{
		Hashtable<String,Vector<String>> ht=new Hashtable<String,Vector<String>>();
		
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\log\\style_changed\\styled_changed_merge.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			String project=line.substring(0,line.indexOf(","));
			line=line.substring(line.indexOf(",")+1,line.length());
			line=line.substring(line.indexOf(",")+1,line.length());
			line=line.substring(line.indexOf(",")+1,line.length());
			String file=line.substring(0,line.indexOf(","));
			if(ht.keySet().contains(project))
			{
				Vector<String> value=ht.get(project);
				if(!value.contains(file))
				{
					value.add(file);
					ht.remove(project);
					ht.put(project, value);
				}
			}
			else
			{
				Vector<String> value=new Vector<String>();
				value.add(file);
				ht.put(project, value);
			}
		}
		br.close();
		
		Set<String> keys=ht.keySet();
		for(String onekey:keys)
		{
//			int count=0;
//			BufferedReader read=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\JavaFileIndex\\"+onekey+".txt"));
//			String idlist="";
//			while((idlist=read.readLine())!=null)
//			{
//				count++;
//			}
//			read.close();
//			
//			System.out.println(ht.get(onekey).size()*1.0/count);
			
			System.out.println(ht.get(onekey).size());
		}
	}
	
	
	
	public static void Visibility() throws Exception
	{
		Hashtable<String,Integer> ht=new Hashtable<String,Integer>();
		
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\log\\style_changed\\styled_changed_merge.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			line=line.substring(line.indexOf(",")+1,line.length());
			int cate=Integer.parseInt(line.substring(0,line.indexOf(",")));
			if(cate==2||cate==3||cate==4)
			{
//				System.out.println(line);
				line=line.substring(line.indexOf(",")+1,line.length());
				int lineno=Integer.parseInt(line.substring(0,line.indexOf(",")));
				line=line.substring(line.indexOf(",")+1,line.length());
				String file=line.substring(0,line.indexOf(","));
				int count=0;
				BufferedReader read=new BufferedReader(new FileReader(file));
				String state="";
				while((state=read.readLine())!=null)
				{
					if(count==lineno)
					{
						if(state.contains("public "))
						{
							if(ht.keySet().contains(cate+"_public"))
							{
								int value=ht.get(cate+"_public");
								value++;
								ht.remove(cate+"_public");
								ht.put(cate+"_public",value);										
							}
							else
							{
								ht.put(cate+"_public",1);
							}
						}
						else if(state.contains("protected "))
						{
							if(ht.keySet().contains(cate+"_protected"))
							{
								int value=ht.get(cate+"_protected");
								value++;
								ht.remove(cate+"_protected");
								ht.put(cate+"_protected",value);										
							}
							else
							{
								ht.put(cate+"_protected",1);
							}
						}
						else if(state.contains("private "))
						{
							if(ht.keySet().contains(cate+"_private"))
							{
								int value=ht.get(cate+"_private");
								value++;
								ht.remove(cate+"_private");
								ht.put(cate+"_private",value);										
							}
							else
							{
								ht.put(cate+"_private",1);
							}
						}
						else
						{
							if(ht.keySet().contains(cate+"_default"))
							{
								int value=ht.get(cate+"_default");
								value++;
								ht.remove(cate+"_default");
								ht.put(cate+"_default",value);										
							}
							else
							{
								ht.put(cate+"_default",1);
							}
						}
							
						break;
					}
					count++;
					
				}
				read.close();
			}
		}
		br.close();
		
		Set<String> keys=ht.keySet();
		for(String onekey:keys)
		{
			System.out.println(onekey+","+ht.get(onekey));
		}
		
		
	}
	
	
	
	public static void CategoryDistribution() throws Exception
	{
		Hashtable<Integer,Integer> truechange=new Hashtable<Integer,Integer>();
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\log\\style_changed\\styled_changed_merge.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			line=line.substring(line.indexOf(",")+1,line.length());
			int cate=Integer.parseInt(line.substring(0,line.indexOf(",")));
			if(truechange.keySet().contains(cate))
			{
				int value=truechange.get(cate);
				value++;
				truechange.remove(cate);
				truechange.put(cate, value);
			}
			else
			{
				truechange.put(cate, 1);
			}
		}
		br.close();
		
		Set<Integer> keys=truechange.keySet();
		for(int onekey:keys)
		{
			System.out.println(onekey+" : "+truechange.get(onekey));
		}
		
	}
	
	
	
	public static void Precentagedistribution() throws Exception
	{
		HashSet<String> allproject=new HashSet<String>();
		Hashtable<String,Integer> truechange=new Hashtable<String,Integer>();
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\log\\style_changed\\styled_changed_merge.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			String project=line.substring(0,line.indexOf(","));
//			System.out.println(project);
			allproject.add(project);
			if(truechange.keySet().contains(project))
			{
				int value=truechange.get(project);
				value++;
				truechange.remove(project);
				truechange.put(project, value);
			}
			else
			{
				truechange.put(project, 1);
			}
		}
		br.close();
		
		
		for(String onepro:allproject)
		{
			int count=0;
			BufferedReader read=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\IdentifierList\\"+onepro+".csv"));
			String idlist="";
			while((idlist=read.readLine())!=null)
			{
				count++;
			}
			read.close();
			System.out.println(truechange.get(onepro)*1.0/count);
		}
		
		System.out.println(allproject.size());
	}
}

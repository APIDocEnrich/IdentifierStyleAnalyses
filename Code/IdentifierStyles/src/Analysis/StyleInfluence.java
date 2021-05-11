package Analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.commons.math3.stat.inference.WilcoxonSignedRankTest;

import wilcoxon.MethodEnum;
import wilcoxon.SignRank;
import wilcoxon.TailEnum;

public class StyleInfluence {
	
	
	
	
	public static void CalculatePercentage() throws Exception
	{
		
        BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\camel_underscore.csv"));
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
					all2.add(ExtractIdentifierName(line));
				else if(line.startsWith("3,"))
					all3.add(ExtractIdentifierName(line));
				else if(line.startsWith("4,"))
					all4.add(ExtractIdentifierName(line));
				else if(line.startsWith("5,"))
					all5.add(ExtractIdentifierName(line));
			}
			br.close();
			
			
			ArrayList<String> all1=new ArrayList<String>();
			
			for(String lines:all11)
			{
				String identifier=ExtractIdentifierName(lines);
				if(identifier.contains("."))
					identifier=identifier.substring(identifier.lastIndexOf(".")+1, identifier.length());
				if(!identifier.trim().isEmpty())
					all1.add(identifier);
			}
			
			ArrayList<String> finallist=new ArrayList<String>();
			finallist.addAll(all1);
			finallist.addAll(all2);
			finallist.addAll(all3);
			finallist.addAll(all4);
			finallist.addAll(all5);
			
			String result2=CalculateNumber(finallist);
			bw.write(project+","+result2);
			bw.newLine();
			
			 
			
		}
		bw.close();
		br1.close();
		
		
	}
	
	public static String ExtractIdentifierName(String line) throws Exception
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
	
	public static String CalculateNumber(ArrayList<String> all) throws Exception
	{
		int underscore=0,camel=0,others=0;
				
		for(String IdentifierName:all)
		{
			if(IdentifierName.contains("_"))
			{
				underscore++;
			}
			else
			{
				LinkedList<String> result=CamelCaseSplit(IdentifierName);
				if(result.size()>1)
				{
					camel++;					
				}
				else if(result.size()==1)
				{
					others++;
				}
			}
	    }
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

	public static void SelectTopRankedProject(int top) throws Exception
	{
		int id=0;
		Vector<ProjectProfile> allproject=new Vector<ProjectProfile>();
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\influence\\camel_underscore.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			String project=line.substring(0,line.indexOf(","));
			System.out.println(id+"  "+project);
			line=line.substring(line.indexOf(",")+1, line.length());
			int undervalue=Integer.parseInt(line.substring(0, line.indexOf(",")));
			line=line.substring(line.indexOf(",")+1, line.length());
			int camelvalue=Integer.parseInt(line.substring(0, line.indexOf(",")));
			line=line.substring(line.indexOf(",")+1, line.length());
			int othervalue=Integer.parseInt(line.substring(0, line.indexOf(",")));
			int sum=Integer.parseInt(line.substring(line.indexOf(",")+1, line.length()));
			if((undervalue+camelvalue+othervalue)==sum)
			{
				float underper=(float)undervalue/sum;
				float camelper=(float)camelvalue/sum;
				float otherper=(float)othervalue/sum;
				
				BufferedReader read=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\GitWebInfo.csv"));
				String info="";
				while((info=read.readLine())!=null)
				{
					if(info.startsWith(project+","))
					{
						info=info.substring(info.indexOf(",")+1, info.length());
						int watchernumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						
						info=info.substring(info.indexOf(",")+1, info.length());
						int starnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						info=info.substring(info.indexOf(",")+1, info.length());
						int forknumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						info=info.substring(info.indexOf(",")+1, info.length());
						int issuenumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						info=info.substring(info.indexOf(",")+1, info.length());
						int requestnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						info=info.substring(info.indexOf(",")+1, info.length());
						int commitnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						info=info.substring(info.indexOf(",")+1, info.length());
						int branchnumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						info=info.substring(info.indexOf(",")+1, info.length());
						int releasenumber=Integer.parseInt(info.substring(0, info.indexOf(",")));
						info=info.substring(info.indexOf(",")+1, info.length());
						int usedby=Integer.parseInt(info.substring(0, info.indexOf(",")));
						int contributor=Integer.parseInt(info.substring(info.indexOf(",")+1, info.length()-1));
						
						int fileno=0;
						int lineno=0;
						BufferedReader reader=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\JavaFileIndex\\"+project+".txt"));
						String oneline="";
						while((oneline=reader.readLine())!=null)
						{
							if(!oneline.contains("?"))
							{
								fileno++;
								BufferedReader redd=new BufferedReader(new FileReader(oneline));
								String state="";
								while((state=redd.readLine())!=null)
								{
									if(!state.trim().isEmpty())
										lineno++;
								}
								redd.close();
							}
						}
						reader.close();
						
						ProjectProfile oneproject=new ProjectProfile(project, underper, camelper, otherper, watchernumber,
								starnumber, forknumber, issuenumber, requestnumber, commitnumber, branchnumber,
								releasenumber, usedby, contributor,fileno,lineno);
						allproject.add(oneproject);
						break;
					}
				}
				read.close();
			}
			else
			{
				System.err.println(project);
			}
			id++;
		}
		br.close();
		
		Vector<ProjectProfile> underselected=new Vector<ProjectProfile>();
		for(int i=0;i<top;i++)
		{
			underselected.add(allproject.get(i));
		}
		for(int i=top;i<allproject.size();i++)
		{
			ProjectProfile onep=underselected.get(0);
			for(ProjectProfile proj:underselected)
			{
				if(proj.getUnderper()<onep.getUnderper())
				{
					onep=proj;
				}
			}
			if(allproject.get(i).getUnderper()>onep.getUnderper())
			{
				underselected.remove(onep);
				underselected.add(allproject.get(i));
			}
		}
		
		Vector<ProjectProfile> camelselected=new Vector<ProjectProfile>();
		for(int i=0;i<top;i++)
		{
			camelselected.add(allproject.get(i));
		}
		for(int i=top;i<allproject.size();i++)
		{
			ProjectProfile onep=camelselected.get(0);
			for(ProjectProfile proj:camelselected)
			{
				if(proj.getCamelper()<onep.getCamelper())
				{
					onep=proj;
				}
			}
			if(allproject.get(i).getCamelper()>onep.getCamelper())
			{
				if(!underselected.contains(allproject.get(i)))
				{
					camelselected.remove(onep);
					camelselected.add(allproject.get(i));
				}
			}
		}
		
		Vector<ProjectProfile> otherselected=new Vector<ProjectProfile>();
		for(int i=0;i<top;i++)
		{
			otherselected.add(allproject.get(i));
		}
		for(int i=top;i<allproject.size();i++)
		{
			ProjectProfile onep=otherselected.get(0);
			for(ProjectProfile proj:otherselected)
			{
				if(proj.getOtherper()<onep.getOtherper())
				{
					onep=proj;
				}
			}
			if(allproject.get(i).getOtherper()>onep.getOtherper())
			{
				if(!underselected.contains(allproject.get(i))&&!camelselected.contains(allproject.get(i)))
				{
					otherselected.remove(onep);
					otherselected.add(allproject.get(i));
				}
			}
		}
		

		
		
		for(ProjectProfile p:underselected)
		{
			BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_watchernumber.txt",true));
			bw.write(p.getWatchernumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_starnumber.txt",true));
			bw.write(p.getStarnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_forknumber.txt",true));
			bw.write(p.getForknumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_issuenumber.txt",true));
			bw.write(p.getIssuenumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_requestnumber.txt",true));
			bw.write(p.getRequestnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_commitnumber.txt",true));
			bw.write(p.getCommitnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_branchnumber.txt",true));
			bw.write(p.getBranchnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_releasenumber.txt",true));
			bw.write(p.getReleasenumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_usedby.txt",true));
			bw.write(p.getUsedby()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_contributor.txt",true));
			bw.write(p.getContributor()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_fileno.txt",true));
			bw.write(p.getFileno()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\under_LOC.txt",true));
			bw.write(p.getLOC()+"");
			bw.newLine();
			bw.close();
		}
		
		for(ProjectProfile p:camelselected)
		{
			BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_watchernumber.txt",true));
			bw.write(p.getWatchernumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_starnumber.txt",true));
			bw.write(p.getStarnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_forknumber.txt",true));
			bw.write(p.getForknumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_issuenumber.txt",true));
			bw.write(p.getIssuenumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_requestnumber.txt",true));
			bw.write(p.getRequestnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_commitnumber.txt",true));
			bw.write(p.getCommitnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_branchnumber.txt",true));
			bw.write(p.getBranchnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_releasenumber.txt",true));
			bw.write(p.getReleasenumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_usedby.txt",true));
			bw.write(p.getUsedby()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_contributor.txt",true));
			bw.write(p.getContributor()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_fileno.txt",true));
			bw.write(p.getFileno()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\camel_LOC.txt",true));
			bw.write(p.getLOC()+"");
			bw.newLine();
			bw.close();
		}
		
		for(ProjectProfile p:otherselected)
		{
			BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_watchernumber.txt",true));
			bw.write(p.getWatchernumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_starnumber.txt",true));
			bw.write(p.getStarnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_forknumber.txt",true));
			bw.write(p.getForknumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_issuenumber.txt",true));
			bw.write(p.getIssuenumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_requestnumber.txt",true));
			bw.write(p.getRequestnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_commitnumber.txt",true));
			bw.write(p.getCommitnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_branchnumber.txt",true));
			bw.write(p.getBranchnumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_releasenumber.txt",true));
			bw.write(p.getReleasenumber()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_usedby.txt",true));
			bw.write(p.getUsedby()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_contributor.txt",true));
			bw.write(p.getContributor()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_fileno.txt",true));
			bw.write(p.getFileno()+"");
			bw.newLine();
			bw.close();
			
			bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\other_LOC.txt",true));
			bw.write(p.getLOC()+"");
			bw.newLine();
			bw.close();
		}
		
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\watchernumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getWatchernumber()+","+camelselected.get(i).getWatchernumber()+","+otherselected.get(i).getWatchernumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\starnumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getStarnumber()+","+camelselected.get(i).getStarnumber()+","+otherselected.get(i).getStarnumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\forknumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getForknumber()+","+camelselected.get(i).getForknumber()+","+otherselected.get(i).getForknumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\issuenumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getIssuenumber()+","+camelselected.get(i).getIssuenumber()+","+otherselected.get(i).getIssuenumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\requestnumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getRequestnumber()+","+camelselected.get(i).getRequestnumber()+","+otherselected.get(i).getRequestnumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\commitnumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getCommitnumber()+","+camelselected.get(i).getCommitnumber()+","+otherselected.get(i).getCommitnumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\branchnumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getBranchnumber()+","+camelselected.get(i).getBranchnumber()+","+otherselected.get(i).getBranchnumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\releasenumber.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getReleasenumber()+","+camelselected.get(i).getReleasenumber()+","+otherselected.get(i).getReleasenumber());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\usedby.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getUsedby()+","+camelselected.get(i).getUsedby()+","+otherselected.get(i).getUsedby());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\contributor.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getContributor()+","+camelselected.get(i).getContributor()+","+otherselected.get(i).getContributor());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\fileno.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getFileno()+","+camelselected.get(i).getFileno()+","+otherselected.get(i).getFileno());
			bw.newLine();
		}
		bw.close();
		
		bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\dump\\LOC.csv"));
		for(int i=0;i<top;i++)
		{
			bw.write(underselected.get(i).getLOC()+","+camelselected.get(i).getLOC()+","+otherselected.get(i).getLOC());
			bw.newLine();
		}
		bw.close();
		
		CalculateP_value(top,underselected,camelselected,otherselected);
		Calculate_average(top,underselected,camelselected,otherselected);
		
	}
	
	public static void Calculate_average(int top,Vector<ProjectProfile> under,Vector<ProjectProfile> camel, Vector<ProjectProfile> other) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\average_"+top+".csv"));
		double arr1[]=new double[top];
		double arr2[]=new double[top];
		double arr3[]=new double[top];
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getWatchernumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getWatchernumber();
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getWatchernumber();
		bw.write("Watchernumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getStarnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getStarnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getStarnumber();
		bw.write("Starnumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getForknumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getForknumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getForknumber();
		bw.write("Forknumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getIssuenumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getIssuenumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getIssuenumber();
		bw.write("Issuenumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getRequestnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getRequestnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getRequestnumber();
		bw.write("Requestnumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getCommitnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getCommitnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getCommitnumber();
		bw.write("Commitnumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getBranchnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getBranchnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getBranchnumber();
		bw.write("Branchnumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getReleasenumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getReleasenumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getReleasenumber();
		bw.write("Releasenumber,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getUsedby();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getUsedby();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getUsedby();
		bw.write("Usedby,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getContributor();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getContributor();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getContributor();
		bw.write("Contributor,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getFileno();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getFileno();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getFileno();
		bw.write("FileNo,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getLOC();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getLOC();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getLOC();
		bw.write("LOC,"+AVE(arr1)+","+AVE(arr2)+","+AVE(arr3));
		bw.newLine();
		
		bw.close();
		
	}
	
	public static double AVE(double arr1[]) throws Exception
	{
		double sum=0;
		for(int i=0;i<arr1.length;i++)
		{
			sum+=arr1[i];
		}
		return sum/arr1.length;
	}
	
	public static void CalculateP_value(int top,Vector<ProjectProfile> under,Vector<ProjectProfile> camel, Vector<ProjectProfile> other) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\project\\IdentifierStyle\\influence\\p_value_"+top+".csv"));
		double arr1[]=new double[top];
		double arr2[]=new double[top];
		double arr3[]=new double[top];
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getWatchernumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getWatchernumber();
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getWatchernumber();
		bw.write("Watchernumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getStarnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getStarnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getStarnumber();
		bw.write("Starnumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getForknumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getForknumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getForknumber();
		bw.write("Forknumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getIssuenumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getIssuenumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getIssuenumber();
		bw.write("Issuenumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getRequestnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getRequestnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getRequestnumber();
		bw.write("Requestnumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getCommitnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getCommitnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getCommitnumber();
		bw.write("Commitnumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getBranchnumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getBranchnumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getBranchnumber();
		bw.write("Branchnumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getReleasenumber();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getReleasenumber();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getReleasenumber();
		bw.write("Releasenumber,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getUsedby();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getUsedby();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getUsedby();
		bw.write("Usedby,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getContributor();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getContributor();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getContributor();
		bw.write("Contributor,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getFileno();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getFileno();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getFileno();
		bw.write("FileNo,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		for(int i=0;i<arr1.length;i++)
			arr1[i]=under.get(i).getLOC();
		for(int i=0;i<arr2.length;i++)
			arr2[i]=camel.get(i).getLOC();		
		for(int i=0;i<arr3.length;i++)
			arr3[i]=other.get(i).getLOC();
		bw.write("LOC,"+P_value_1(arr1,arr2)+","+P_value_2(arr1,arr2)+","+P_value_1(arr1,arr3)+","+P_value_2(arr1,arr3)+","+P_value_1(arr2,arr3)+","+P_value_2(arr2,arr3));
		bw.newLine();
		
		bw.close();
		
	}
	
	public static double P_value_1(double arr1[], double arr2[])
	{
		double alpha = 0.05;
		MethodEnum method = MethodEnum.APPROXIMATE;
		TailEnum tail = TailEnum.BOTH;
		
		SignRank sr = new SignRank(arr1, arr2, alpha, tail, method);
		return sr.getP();
		
	}
	
	public static double P_value_2(double arr1[], double arr2[])
	{
		WilcoxonSignedRankTest one=new WilcoxonSignedRankTest();
		
		return one.wilcoxonSignedRankTest(arr1, arr2,false);
	}

}

class ProjectProfile
{
	String project;
	float underper;
	float camelper;
	float otherper;
	int watchernumber;
	int starnumber;
	int forknumber;
	int issuenumber;
	int requestnumber;
	int commitnumber;
	int branchnumber;
	int releasenumber;
	int usedby;
	int contributor;
	int fileno;
	int LOC;
	
	
	public int getFileno() {
		return fileno;
	}
	public void setFileno(int fileno) {
		this.fileno = fileno;
	}
	public int getLOC() {
		return LOC;
	}
	public void setLOC(int lOC) {
		LOC = lOC;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public float getUnderper() {
		return underper;
	}
	public void setUnderper(float underper) {
		this.underper = underper;
	}
	public float getCamelper() {
		return camelper;
	}
	public void setCamelper(float camelper) {
		this.camelper = camelper;
	}
	public float getOtherper() {
		return otherper;
	}
	public void setOtherper(float otherper) {
		this.otherper = otherper;
	}
	public int getWatchernumber() {
		return watchernumber;
	}
	public void setWatchernumber(int watchernumber) {
		this.watchernumber = watchernumber;
	}
	public int getStarnumber() {
		return starnumber;
	}
	public void setStarnumber(int starnumber) {
		this.starnumber = starnumber;
	}
	public int getForknumber() {
		return forknumber;
	}
	public void setForknumber(int forknumber) {
		this.forknumber = forknumber;
	}
	public int getIssuenumber() {
		return issuenumber;
	}
	public void setIssuenumber(int issuenumber) {
		this.issuenumber = issuenumber;
	}
	public int getRequestnumber() {
		return requestnumber;
	}
	public void setRequestnumber(int requestnumber) {
		this.requestnumber = requestnumber;
	}
	public int getCommitnumber() {
		return commitnumber;
	}
	public void setCommitnumber(int commitnumber) {
		this.commitnumber = commitnumber;
	}
	public int getBranchnumber() {
		return branchnumber;
	}
	public void setBranchnumber(int branchnumber) {
		this.branchnumber = branchnumber;
	}
	public int getReleasenumber() {
		return releasenumber;
	}
	public void setReleasenumber(int releasenumber) {
		this.releasenumber = releasenumber;
	}
	public int getUsedby() {
		return usedby;
	}
	public void setUsedby(int usedby) {
		this.usedby = usedby;
	}
	public int getContributor() {
		return contributor;
	}
	public void setContributor(int contributor) {
		this.contributor = contributor;
	}
	public ProjectProfile(String project, float underper, float camelper, float otherper, int watchernumber,
			int starnumber, int forknumber, int issuenumber, int requestnumber, int commitnumber, int branchnumber,
			int releasenumber, int usedby, int contributor, int fileno,int LOC) {
		super();
		this.project = project;
		this.underper = underper;
		this.camelper = camelper;
		this.otherper = otherper;
		this.watchernumber = watchernumber;
		this.starnumber = starnumber;
		this.forknumber = forknumber;
		this.issuenumber = issuenumber;
		this.requestnumber = requestnumber;
		this.commitnumber = commitnumber;
		this.branchnumber = branchnumber;
		this.releasenumber = releasenumber;
		this.usedby = usedby;
		this.contributor = contributor;
		this.fileno=fileno;
		this.LOC=LOC;
	}
	
	
	
}



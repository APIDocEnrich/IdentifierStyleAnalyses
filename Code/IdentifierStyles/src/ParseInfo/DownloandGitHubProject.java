package ParseInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class DownloandGitHubProject {
	
	public static void main(String args[]) throws Exception
	{
		int count=0;
		BufferedReader br=new BufferedReader(new FileReader("D:\\project\\IdentifierStyle\\data\\repository_state.csv"));
		String line="";
		while((line=br.readLine())!=null)
		{
			int index=line.indexOf("github.com/");
			String url="https://"+line.substring(index, line.lastIndexOf(".git"));
			System.out.println(url);
			getGitHubProjects(url);
			count++;
		}
		br.close();
		System.out.println(count);
	}
	
	
	//Download GitHub Projects
	public static void getGitHubProjects(String line) throws Exception {
		try {
			
			URL url=new URL(line);
			SSLContext sslctx = SSLContext.getInstance("TLS");
			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
				public X509Certificate[] getAcceptedIssuers(){return null;}
				public void checkClientTrusted(X509Certificate[] certs, String authType){}
				public void checkServerTrusted(X509Certificate[] certs, String authType){}
			}};
			sslctx.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslctx.getSocketFactory());
			//HttpsURLConnection yc = (HttpsURLConnection)url.openConnection();
			URLConnection yc = url.openConnection();
			BufferedReader in = null;
			boolean contains=false;
			try{
				in = new BufferedReader(
						new InputStreamReader(
								yc.getInputStream()));
				
				String inputLine;
				while ((inputLine = in.readLine()) != null)
				{
					//if(inputLine.contains("build.xml")){
						contains=true;
					//}
				}
				inputLine+="";
			}

			catch(Exception e) {
				System.out.println(e.getMessage());;
			}

			

            //System.out.println(inputLine);
			if(contains)
			{
//				System.out.println("Yes Cloning");
				String[] command =
					{
						"cmd",
					};
				Process p = Runtime.getRuntime().exec(command);
				new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
				new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
				PrintWriter stdin = new PrintWriter(p.getOutputStream());

				stdin.println("D:");
				stdin.println("cd D:\\project\\IdentifierStyle\\data\\GitProject\\");
				String[] split=line.split("/");
				if(split.length>4){
					stdin.println("git clone "+line+" "+split[3]+"_"+split[4]);
				}
				else{
					stdin.println("git clone "+line+" "+split[3]+"_"+split[3]);
				}
				// write any other commands you want here
				stdin.close();
				int returnCode = p.waitFor();
				System.out.println("Return code = " + returnCode);
			}

			
		}catch (Exception e) {

			throw e;
		}
	}
	

}


class SyncPipe implements Runnable
{
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
		istrm_ = istrm;
		ostrm_ = ostrm;
	}
	public void run() {
		try
		{
			final byte[] buffer = new byte[1024];
			for (int length = 0; (length = istrm_.read(buffer)) != -1; )
			{
				ostrm_.write(buffer, 0, length);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private final OutputStream ostrm_;
	private final InputStream istrm_;
}

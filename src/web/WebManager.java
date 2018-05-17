package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.ImageManager;
import views.Table;

public class WebManager {
	private HashSet<String> list;
	private ArrayList<Thread> threadList;
	private HashSet<String> imageList;
	
	public WebManager() {
		list = new HashSet<>();
		threadList = new ArrayList<>();
		imageList = new HashSet<>();
	}

//	private static final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.0.73", 8080));

	public HashSet<String> read() throws IOException {
		HashSet<String> list = new HashSet<>();
		URL url = new URL("https://alpha.wallhaven.cc/toplist");
		URLConnection hc = url.openConnection();
		hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		BufferedReader in = new BufferedReader(
				new InputStreamReader(hc.getInputStream()));
		String webText = "";
		String inputLine;
		while ((inputLine = in.readLine()) != null)
			webText += inputLine + "\n";
		in.close();
		Pattern pattern = Pattern.compile("(src=\"https://alpha.wallhaven.cc/wallpapers/thumb/small/)([a-zA-Z\\d/-])*(\\.[a-zA-Z]{3})");
		Matcher matcher = pattern.matcher(webText);
		while(matcher.find()) {
			String c =  matcher.group(0).substring(5);
			String p[] = c.split("/");
			list.add(c);
			imageList.add(p[6]);
		}
		return list;
	}

	public HashSet<String> getImageList() {
		return imageList;
	}

	public String copy(String img) throws IOException {
		Path path;
		URL website = new URL(img);
		URLConnection hc = website.openConnection();
		hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		try (InputStream in = hc.getInputStream()) {
			path = Paths.get("./data/", new File(img).getName());
			Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
			list.add(path.getFileName().toUri().getPath());
		}
		Table.changeDownload(img);
		return path.getFileName().toString();
	}
	
	public void list(HashSet<String> listLinks) throws InterruptedException{
		for (String hashSet : listLinks) {
			threadList.add(new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ImageManager.changeImage(copy(hashSet));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}));
		}
	}
	
	public void initThread() {
		for (Thread thread : threadList) {
			thread.start();
		}
	}
	
	public HashSet<String> getList(){
		return list;
	}

	public void stopThread() {
		for (@SuppressWarnings("unused") Thread thread : threadList) {
//			thread.stop();
		}
	}
}

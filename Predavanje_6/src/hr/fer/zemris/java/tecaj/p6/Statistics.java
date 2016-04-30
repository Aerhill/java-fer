package hr.fer.zemris.java.tecaj.p6;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {

	private static class ExtensionInfo {
		private String extension;
		private long totalSize;
		private int count;

		public ExtensionInfo(String extension) {
			this.extension = extension;
		}

		public int getCount() {
			return count;
		}

		public String getExtension() {
			return extension;
		}

		public long getTotalSize() {
			return totalSize;
		}

		public double getAverageSize() {
			return totalSize / (double) count;
		}

		private void update(long size) {
			totalSize += size;
			count++;
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dear user");
			return;
		}

		File parent = new File(args[0]);
		if (!parent.isDirectory()) {
			System.out.println("must be dir");
			return;
		}

		Map<String, ExtensionInfo> mapa = calcStats(parent);
		List<ExtensionInfo> statsList = new ArrayList<>(mapa.values());

		Collections.sort(statsList, (s1, s2) -> Double.compare(s1.getAverageSize(), s2.getAverageSize()));

		for (ExtensionInfo e : statsList) {
			System.out.printf("%s %d %d %f%n", e.getExtension(), e.getCount(), e.getTotalSize(), e.getAverageSize());
		}

	}

	private static Map<String, ExtensionInfo> calcStats(File parent) {
		Map<String, ExtensionInfo> hm = new HashMap<>();
		calcStatsRecursive(parent, hm);
		return hm;
	}

	private static void calcStatsRecursive(File parent, Map<String, ExtensionInfo> stats) {
		File[] children = parent.listFiles();
		if(children == null) return;
		for (File file : children) {
			if(file.isFile()) {
				updateMap(file,stats);
			} else if(file.isDirectory()) {
				calcStatsRecursive(file, stats);
			}
		}
	}

	private static void updateMap(File file, Map<String, ExtensionInfo> stats) {
		String fileName = file.getName();
		int pos = fileName.lastIndexOf(".");
		if( pos < 1 || pos == fileName.length()-1){
			return;
		}
		
		String ext = fileName.substring(pos+1).toLowerCase();
		ExtensionInfo einfo = stats.get(ext);
		if(einfo == null) {
			einfo = new ExtensionInfo(ext);
			stats.put(ext, einfo);
		} 
		einfo.update(file.length());
	}

}

package com.servlet.browse;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

 
public class Files {
	protected File repertoire;
	
	public Files(File repertoire) {
		super();
		this.repertoire = repertoire;
	}

	public File[] getFilesByType(final String type) {
		File[] list = {};
		if (this.repertoire.isDirectory()) {
			list = this.repertoire.listFiles(new FilenameFilter() {
				public boolean accept(File file, String fileName) {
					return fileName.endsWith(type);
				}
			});

		}
		return list;
	}
}
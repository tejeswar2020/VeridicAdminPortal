package com.adminportal.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.adminportal.service.UserService;

public class FeatureCatalog
{
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@SuppressWarnings("unused")
	private static final ResourceBundle bundle = ResourceBundle.getBundle(FeatureCatalog.class.getName());
	
	public static File createOrRetrieve(final String target) throws IOException
	{
	    final Path path = Paths.get(target);

	    if(Files.notExists(path))
	    {
	        LOG.info("Target file \"" + target + "\" will be created.");
	        return Files.createFile(Files.createDirectories(path)).toFile();
	    }
	    
	    LOG.info("Target file \"" + target + "\" will be retrieved.");
	    return path.toFile();
	}
	
	public static String getExtensionOfFile(MultipartFile file)
	{
		String fileExtension="";
		// Get file Name first
		String fileName=file.getOriginalFilename();
		
		// If fileName do not contain "." or starts with "." then it is not a valid file
		if(fileName.contains(".") && fileName.lastIndexOf(".")!= 0)
		{
			fileExtension = "." + fileName.substring(fileName.lastIndexOf(".")+1);
		}
		
		return fileExtension;
	}
}

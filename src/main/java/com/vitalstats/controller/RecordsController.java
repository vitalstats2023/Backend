package com.vitalstats.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vitalstats.models.HealthFiles;
import com.vitalstats.models.HealthFolder;
import com.vitalstats.models.HealthRecords;
import com.vitalstats.request.HealthFolderRequest;
import com.vitalstats.request.IdRequest;
import com.vitalstats.request.UserIdRequest;
import com.vitalstats.service.RecordService;
import com.vitalstats.utils.Utils;

@RestController
@RequestMapping("/api/record")
public class RecordsController {
	
	
	 @Autowired
	 private RecordService recordService;
	 
	 
	 @GetMapping("/viewallrecords")
	 public ResponseEntity<HealthRecords> getAllHealthRecordsByUser(@RequestBody UserIdRequest userIdRequest) {
	    HealthRecords healthRecords = recordService.getAllHealthRecordsByUser(userIdRequest.getUserId());
	    return healthRecords != null ? ResponseEntity.ok(healthRecords) : ResponseEntity.noContent().build();
	 }
	 
	 @PostMapping("/createfolder")
	    public ResponseEntity<HealthFolder> createHealthFolder(@RequestBody HealthFolderRequest request) {
		 HealthFolder folder = new HealthFolder();
		 folder.setName(request.getFolderName());
	     HealthFolder savedFolder = recordService.saveHealthFolder(folder, request.getUserId());
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedFolder);
	 }
	 
	 @PutMapping("updatefolder")
	 public ResponseEntity<?> updateHealthFolder(@RequestBody HealthFolderRequest request){
		 HealthFolder folder = recordService.getHealthFolderById(request.getFolderId()).get();
		 if(folder!=null) {
			 folder.setName(request.getFolderName());
		 }
		 return ResponseEntity.ok(recordService.saveHealthFolder(folder, request.getUserId()));
	 }
	 
	 @GetMapping("/viewallfolders")
	 public ResponseEntity<List<HealthFolder>> getAllHealthFoldersByUser(@RequestBody UserIdRequest userIdRequest) {
		 List<HealthFolder> folders = recordService.getAllHealthFoldersByUser(userIdRequest.getUserId());
	     return folders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(folders);
	 }
	 
	 @DeleteMapping("/deletefolder")
	 public ResponseEntity<String> deleteHealthFolder(@RequestBody IdRequest request) {
	     recordService.deleteHealthFolder(request.getId());
	     return ResponseEntity.ok("Folder Deleted Successfully");
	 }
	 
	 @PostMapping("/uploadfile")
	 public ResponseEntity<?> uploadFileToFolder( @RequestParam UUID folderId,@RequestParam MultipartFile file ) throws Exception{
		 HealthFiles files= new HealthFiles();		 
		  // Model  Call need to GO
		 files.setFile(Utils.encodeImage(file));
		 files.setFileType(file.getContentType());
		HealthFiles savedFiles= recordService.saveHealthFile(files, folderId);
		return ResponseEntity.ok(savedFiles);
	 }
	 @GetMapping("/viewfiles")
	 public ResponseEntity<?> viewFilesFromFolder(@RequestBody IdRequest  request){
		 
		 List<HealthFiles> files= recordService.getAllHealthFilesByFolder(request.getId());
		 return ResponseEntity.ok(files);
	 }
	 
	 @PutMapping("/updatefile")
	 public ResponseEntity<?> updateFileFromFolder(){
		 
		 return null;
	 }
	 
	 @DeleteMapping("/deletefile")
	 public ResponseEntity<?> deleteFileFromFolder(@RequestBody IdRequest request){
		 recordService.deleteHealthFile(request.getId());
		 return ResponseEntity.ok("File Deleted SucessFully");
	 }
	 
	 @PostMapping("/upload")
	 public String uploadImage(@RequestParam MultipartFile file) throws IOException {
		 
		 if (file.isEmpty()) {
	         return "File is empty";
	     }
	     String fileType = file.getContentType();
	     byte[] fileContent = file.getBytes();
	     String encodedString = Base64.getEncoder().encodeToString(fileContent);
	     String base64Image = "data:" + fileType + ";base64," + encodedString;
	     return base64Image;
	 }
	 

	                      
}

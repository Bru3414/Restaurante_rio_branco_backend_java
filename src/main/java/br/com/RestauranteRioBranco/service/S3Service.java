package br.com.RestauranteRioBranco.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Service
public class S3Service {
	
	private final AmazonS3 amazonS3;
	
	private final String bucket = "resriobranco-images";
	
	@Value("${projeto.accessKey}")
	private String accessKey;
	
	@Value("${projeto.secretKey}")
	private String secretKey;
	
	public S3Service() {
		var credentials = new BasicAWSCredentials(accessKey, secretKey);
		amazonS3 = AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.SA_EAST_1)
				.build();
	}
	
	public void uploadImage(File image, String name) {
		amazonS3.putObject(bucket, name, image);
	}
	
	public List<String> findAllImages() {
		var list = amazonS3.listObjects(bucket);
		return list.getObjectSummaries()
				.stream()
				.map(item -> item.getKey())
				.collect(Collectors.toList());
	}
	
	public void deleteImage(String name) {
		amazonS3.deleteObject(bucket, name);
	}

}

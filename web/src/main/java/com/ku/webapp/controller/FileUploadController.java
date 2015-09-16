package com.ku.webapp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ku.Constants;
import com.ku.common.KUException;
import com.ku.service.OfferManager;

/**
 * Controller class to upload Files.
 * <p/>
 * <p>
 * <a href="FileUploadFormController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
public class FileUploadController extends BaseFormController {

	private OfferManager offerManager;

	public FileUploadController() {
		setCancelView("redirect:/home");
		setSuccessView("uploadDisplay");
	}

	@Autowired
	public void setOfferManager(OfferManager offerManager) {
		this.offerManager = offerManager;
	}

	@ModelAttribute
	@RequestMapping(value = "/fileupload", method = RequestMethod.GET)
	public FileUpload showForm() {
		return new FileUpload();
	}

	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	public String onSubmit(FileUpload fileUpload, BindingResult errors,
			HttpServletRequest request) throws Exception {

		if (request.getParameter("cancel") != null) {
			return getCancelView();
		}

		if (validator != null) { // validator is null during testing
			validator.validate(fileUpload, errors);

			if (errors.hasErrors()) {
				return "fileupload";
			}
		}

		// validate a file was entered
		if (fileUpload.getFile().length == 0) {
			Object[] args = new Object[] { getText("uploadForm.file",
					request.getLocale()) };
			errors.rejectValue("file", "errors.required", args, "File");

			return "fileupload";
		}

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("file");

		// the directory to upload to
		String uploadDir = getServletContext().getRealPath("/resources");

		// The following seems to happen when running jetty:run
		if (uploadDir == null) {
			uploadDir = new File("src/main/webapp/resources").getAbsolutePath();
		}
		uploadDir += "/" + request.getRemoteUser() + "/";

		// Create the directory if it doesn't exist
		File dirPath = new File(uploadDir);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		// retrieve the file data
		InputStream stream = file.getInputStream();

		// write the file to the file specified
		OutputStream bos = new FileOutputStream(uploadDir
				+ file.getOriginalFilename());
		int bytesRead;
		byte[] buffer = new byte[8192];

		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}

		bos.close();

		// close the stream
		stream.close();

		// place the data into the request for retrieval on next page
		request.setAttribute("friendlyName", fileUpload.getName());
		request.setAttribute("fileName", file.getOriginalFilename());
		request.setAttribute("contentType", file.getContentType());
		request.setAttribute("size", file.getSize() + " bytes");
		request.setAttribute("location", dirPath.getAbsolutePath()
				+ Constants.FILE_SEP + file.getOriginalFilename());

		String link = request.getContextPath() + "/resources" + "/"
				+ request.getRemoteUser() + "/";
		request.setAttribute("link", link + file.getOriginalFilename());

		return getSuccessView();
	}

	@RequestMapping(value = "/importCSV", method = RequestMethod.GET)
	public ModelAndView showImportCSV() {
		Model model = new ExtendedModelMap();
		model.addAttribute("fileUpload", new FileUpload());
		return new ModelAndView("/fileupload", model.asMap());
	}

	@RequestMapping(value = "/importCSV", method = RequestMethod.POST)
	public ModelAndView importCSV(FileUpload fileUpload, BindingResult errors,
			HttpServletRequest request) {
		Model model = new ExtendedModelMap();
		try {
			if (request.getParameter("cancel") != null) {
				return new ModelAndView("/fileupload", model.asMap());
			}

			if (validator != null) { // validator is null during testing
				validator.validate(fileUpload, errors);

				if (errors.hasErrors()) {
					saveError(request, errors.toString());
					return new ModelAndView("/fileupload", model.asMap());
				}
			}

			// validate a file was entered
			if (fileUpload.getFile().length == 0) {
				Object[] args = new Object[] { getText("uploadForm.file",
						request.getLocale()) };
				errors.rejectValue("file", "errors.required", args, "File");

				saveError(request, errors.getAllErrors().toString());
				return new ModelAndView("/fileupload", model.asMap());
			}

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
					.getFile("file");

			// the directory to upload to
			String uploadDir = getServletContext().getRealPath("/images");
			
			// The following seems to happen when running jetty:run
			if (uploadDir == null) {
				uploadDir = new File("src/main/webapp/resources")
						.getAbsolutePath();
			}
			uploadDir += Constants.FILE_SEP+".."+Constants.FILE_SEP+".."+Constants.FILE_SEP+"file"+Constants.FILE_SEP + "csv" + Constants.FILE_SEP
					+ request.getRemoteUser() + "-" +new Date() + Constants.FILE_SEP;

			// Create the directory if it doesn't exist
			File dirPath = new File(uploadDir);
			if (!dirPath.exists()) {
				dirPath.mkdirs();
			}

			// retrieve the file data
			InputStream stream = file.getInputStream();

			// write the file to the file specified
			OutputStream bos = new FileOutputStream(uploadDir
					+ file.getOriginalFilename());
			int bytesRead;
			byte[] buffer = new byte[8192];

			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			bos.close();

			// close the stream
			stream.close();
			String link = dirPath.getAbsolutePath()
					+ Constants.FILE_SEP + file.getOriginalFilename();
			// place the data into the request for retrieval on next page
			request.setAttribute("friendlyName", fileUpload.getName());
			request.setAttribute("fileName", file.getOriginalFilename());
			request.setAttribute("contentType", file.getContentType());
			request.setAttribute("size", file.getSize() + " bytes");
			request.setAttribute("location", link);

			offerManager.importOfferLabels(link);
			return new ModelAndView("/fileupload", model.asMap());
		} catch (KUException e) {
			log.error(e.getMessage(), e);
			saveError(request, e.getMessage());
			return new ModelAndView("/fileupload", model.asMap());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			saveError(request, e.getMessage());
			return new ModelAndView("/fileupload", model.asMap());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			saveError(request, e.getMessage());
			return new ModelAndView("/fileupload", model.asMap());
		}
	}
}

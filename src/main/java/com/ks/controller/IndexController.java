package com.ks.controller;

import com.ks.bean.UserRule;
import com.ks.service.RegisteredEventsService;
import com.ks.service.UserRuleService;
import com.ks.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Locale;


@Controller
public class IndexController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	RegisteredEventsService registeredEventsService;

	@Autowired
	private UserRuleService userRuleService;

	@GetMapping(value = "/")
	public String login(@RequestParam(required = false) Boolean authenticate,
						@RequestParam(required = false) Boolean authorize, HttpServletRequest request, Locale locale, Model model,
						@RequestParam(required = false) String msg) {
		if (authenticate != null && authenticate.equals(false)
				&& request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {
			model.addAttribute(Consts.ERROR, messageSource.getMessage("error.invalide.credentials", null, locale));
		} else if (authorize != null && authorize.equals(false)) {
			model.addAttribute(Consts.ERROR, messageSource.getMessage("error.access.denied", null, locale));
		} else if (authenticate != null || authorize != null) {
			model.addAttribute(Consts.ERROR, messageSource.getMessage(Consts.ERROR, null, locale));
		} else if (!StringUtils.isEmpty(msg) && "expire".equalsIgnoreCase(msg)) {
			model.addAttribute(Consts.ERROR, messageSource.getMessage("error.session.expire", null, locale));
		}
		return "login";
	}

	@GetMapping(value = "/admin/change/password")
	public String changePassword(@RequestParam(required = false) Boolean authenticate,
						@RequestParam(required = false) Boolean authorize, HttpServletRequest request, Locale locale, Model model,
						@RequestParam(required = false) String msg) {

		return "login";
	}

	@GetMapping("/admin/list/signature")
	public String getListOfSignature(Model model) {
		model.addAttribute("userRules",Collections.emptyList());
		return "userRuleList";
	}

	@GetMapping(value = {"/admin/add/signature","/admin/edit/signature/{id}"})
	public String getSignature(Model model, @PathVariable(required = false) Integer id) {
		model.addAttribute("ruleTypes", Consts.RULE_TYPES);
		model.addAttribute("userRule", id == null? new UserRule():userRuleService.getUserRule(id));
		return "addEditUserRule";
	}

	@PostMapping("/admin/add/signature")
	public String addSignature(@ModelAttribute @Valid UserRule userRule, BindingResult result, Model model, Locale locale) {

		model.addAttribute("userRule", userRule);
		model.addAttribute("ruleTypes", Consts.RULE_TYPES);
		if (result.hasErrors()) {
			model.addAttribute(Consts.ERROR, messageSource.getMessage("error", null, locale));
			return "addEditUserRule";
		}
		int idUserRule = 0;
		try {
			idUserRule = userRuleService.addOrUpdateUserRule(userRule);
			model.addAttribute(Consts.INFO_MSG, messageSource.getMessage("save.msg", null, locale));
		} catch (Exception e) {
			model.addAttribute(Consts.ERROR_MSG, messageSource.getMessage("error", null, locale));
			return "addEditUserRule";
		}
		return "redirect:/admin/edit/signature/" + idUserRule;
	}

	@PostMapping("/admin/edit/signature/{id}")
	public String editSignature(@PathVariable Integer id, @ModelAttribute @Valid UserRule userRule, Model model) {
		model.addAttribute("ruleTypes", Consts.RULE_TYPES);
		return "addEditUserRule";
	}

	@GetMapping("/admin/delete/signature/{id}")
	public String deleteSignature(Model model) {
		return "addEditUserRule";
	}

	@GetMapping(value={"/admin/list/registered/event","/admin/list/archive/registered/event"})
	public String getRegisteredEventList(Model model, HttpServletRequest request) {
		boolean isArchive = false;
		if(request.getRequestURI().contains("/archive/")){
			isArchive = true;
		}
		model.addAttribute("registeredEvents",registeredEventsService.getList());
		model.addAttribute("isArchive", isArchive);
		return "registeredEventList";
	}
}


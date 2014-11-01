package ar.edu.itba.grupo2.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.grupo2.domain.comment.Comment;
import ar.edu.itba.grupo2.domain.film.Film;
import ar.edu.itba.grupo2.domain.film.FilmRepo;
import ar.edu.itba.grupo2.domain.film.UserCantCommentException;
import ar.edu.itba.grupo2.domain.film.UserIsntAdminException;
import ar.edu.itba.grupo2.domain.genre.Genre;
import ar.edu.itba.grupo2.domain.image.MovieImage;
import ar.edu.itba.grupo2.domain.user.User;
import ar.edu.itba.grupo2.domain.user.UserRepo;
import ar.edu.itba.grupo2.web.command.CommentForm;
import ar.edu.itba.grupo2.web.command.FilmForm;
import ar.edu.itba.grupo2.web.command.validator.CommentFormValidator;
import ar.edu.itba.grupo2.web.command.validator.FilmFormValidator;

@Controller
@RequestMapping(value = "film")
public class FilmController extends BaseController {
	
	private final FilmRepo filmRepo;
	private final CommentFormValidator commentValidator;
	private final FilmFormValidator filmValidator;
	
	@Autowired
	public FilmController(FilmRepo filmRepo, UserRepo userRepo, CommentFormValidator commentValidator, FilmFormValidator filmValidator) {
		super(userRepo);
		this.filmRepo = filmRepo;
		this.commentValidator = commentValidator;
		this.filmValidator = filmValidator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("topfive", filmRepo.getTop(5));
		mav.addObject("latest", filmRepo.getLatest(5));
		mav.addObject("newReleases",
				filmRepo.getNewests(7));
		
		mav.setViewName("welcome");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView filmDetails(HttpSession session, @RequestParam(value = "id", required=false) Film film) {
		ModelAndView mav = new ModelAndView();
		
		MovieImage movieImage = film.getMovieImage();
		String content = "iVBORw0KGgoAAAANSUhEUgAAAIkAAACJCAYAAAAYJBvJAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6RDQ1OTBFRUY5MDA2MTFFM0EyMkFEQTVCM0NCRTk4NzUiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6RDQ1OTBFRjA5MDA2MTFFM0EyMkFEQTVCM0NCRTk4NzUiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDpENDU5MEVFRDkwMDYxMUUzQTIyQURBNUIzQ0JFOTg3NSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDpENDU5MEVFRTkwMDYxMUUzQTIyQURBNUIzQ0JFOTg3NSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PtkRSLYAABX7SURBVHja7J0JeFvVlYCPnnbJsmRbduItq+04xtnrkEAWhwQmJEAappR9aejQss0wbQf4ZikwLDNfWRpCm7ZspZ22HyEByhIoBbJAIAkhqxMntuPEWe043jctlvXmnKfFkrVLT5ae9I6/kxdb0nvSfb/OPefcc++VsCwLoogSTGT0j0Qi4e2ErW3HyvFQjToHtQy1FFWHmiE2d1zEgtqHehq1FvUw6hbUvXnG8qFYT05GRML9EyMkCMZ0PNyGejNqkXjfkkK6Ud9CfQNh+TphkCAcy/HwKOpi8Z4ktexGfQb1AwSGHRVIEI4ZePgV6gKx/QUlO1DvQVCOxg0ShEOJh6dRH0KVim0uSLGi/hz1F+FYlYggQUDIAX0TdbbYzikh76PegaB08wIJAnI5Ht5DzRHbNqVkL+rVCMrFYJAwYViQa/HwqQhISgqlKbbgPTYGe1JQS4IvXoKHzahqsT1TWihEXooWxRyRJUFAKvDwVxGQtJDLUF8O9CATABDKkL6Nmim2n7BkzxfbYeNrr0bz0tvxvt8SNiQoL6GWi00uLGlqqIf1Tz0JX374Hhzc8UU0p1iPoIwJCQk+aSke7hSbXHiAPHH/fSBjJFBWMg52frgpmtPowZEHCwwJAkIDfr8Rm1xYcmTfPjcgM6aVgUwaU55zDXJQGcyS0CBdqdjswpHNG96EJx64F1RKGcyoLAUpw3ARSQwlIBTq/ptfSJAe+v9/is0uDOnv64VnH30Y/vDiL2F8cSFUlk/mUhl2u92tMcjNyEO+6xeZxwPki0wWm9+/Oa/dv8/rbxWzZsOEslLQZugSEsG8gXB0tbVBZUUZZOl1CAWvxWNyVIp0nh8Jyd0iDt5gfPvldti2eTP3rQ0kE0rLYPGKlVC9cuWoAEPdC1mPXGMOzKycAgqFPFarEUhudEHCZVwvttcp8P8dqNp0hYIansCgKMGf5ORkQ5bBO23U2dUDPT29MDg4yP1evfIauGHNDyE3Pz9u75NyIF9//AEUF3pHqjZWAoMWM6hVSvfffvLiy7FeLi83Z8pFlyWZl86AuELIkYAQGLk5WdhSWX4jhnHOG9U/YIYLFzvgq79/gpbnQ7jvP/6LAyZewqBz6rIe2WOL4OrbfwAv/+J/gR20gVIh5/NSVEy2yQVJdbp3L7n5BVBe1g36zAzQalQRvZaeP2l8AaetbZ3wxi+fjxskE0pLwWi8A7755AO44vu3Q1X1EvdjBM7QEK9dzzxPSCrTHpKx+dB3viliQHzsszGL08bDNTC5chrv77Nq0WI4WXcMvlN9Bej0eq/HhggS+xCfl5viGQKLKXgUs8XK27k+/dMrcXufE6eU+wDiaUlcyoOUekY3hSIi/EKSCHHAwaslGesJiS7dAandv1fwnyEOPkmGJyRyEEX4kPBvSaSekIiCotdpQcjTXslp5RkSECFJMRmys2AbsouQxFNiHD1Ngu6GLAnD+3kZEQ2HUEo+NRzXIbeKliQelsRpTYTb3diBEbub+MjF5mZHHiBDA0JerYWLbiSi4xoXaXVCAgJf0MfR3fC31oxEwoiQuGSgrxfUarXQGeG6G977XxEShzQ1NIBKpcQ2Eb4l4ZcR0ZIM+yQtzSCTSUHgjHCQ8Ol4s3YJK0LidlzPg5brboRuSWheN3+fYcjGrWUi5km47qa+ARRKhSME5lFHHRLW7qwpsfPgn0jANGCWipA4w18qdFbIZe6MK186mkIF2VqdnrMmLo1FFHItdHd1iN2NZ/iroQJiAXc3d/7Lv4IutwBO1NXzEPpKgGEIkk6ZCAkAN59GpVIBI2WE7rfC9bfcyMt51KocOHf6FHWaEhESZ2SjUioDdg9m6yCoFOlTbqNWZoMN3dXm86fJovSLkHBOaz3ogtSRKDJzgDV1p3w7UHaVLIgEFHCkdrerPc6Kjis45tvQLDhqk5GaP7kcJkyZCha0Jv4eD6VCEblMAzptIb5pORw5tAdMpn7XQ41pb0lc5QEqCn/93NWymXNAk6mHA9s/w4ZMvaaSSZXoj2XjUQVdnW3QUFcDVqvF8yl70h4S14w9tUrhN7MxaWoFl8U0mUyg12lSqGuR4mfOxjA3A8ymATh+8gC0t7X4e+rWtIfk1PEG0Gfq/FoRtU4POWMc0zh7e/thTG52SnxmuVyL4b4RbDYbnDheCy3NZwL5Y+SPfCl2N/v2glqt8us/TJ17ufv/xSUlwA5ZhG4/OMdUqdBB64VzcLLxGIIyGOwFzy9Ztsoe1mK/qSqUZaVsKzf66ydbWjx5eMEnY35h0mdcQwGi1eQBI1HC0SP7ON8jBCC0b8561y9pC0mt02nVcpbE9wZPKh+e+Tq+pBT6+k2ChYT8D9sgCwf374SO9tZQT+9B/T5aEasIyf59IJVKuRKBkTe3ZGYVKFTDE8dpXMTKhcHCg4TCW9Yuh8MY2lrMplBPpzXmr0JAjnj+MW0hofBXl6H1e3PLZnpvxJGXnw8mszliSHq7uxLezSgVWXD08F6wWsyhnvwJ6hwEZLdPqJyu/giFv4X5Y/06rUUTJ3n9TisXmUyWiBNkPZ1doNMbEvY5KcQ9d7YJBgb6gj2NVgX+H4TjbwHzKekIyZ7t27mjRuNbsqjJNIBx7Fif14wrxQjH0i+sD8oq4DxCEsQ5fQjh+DTUadKyu6k9sJ/zRxzpeO8uonLeQr+vyS0oEpRPImUU0NrS7K/ulf7wnLNr+TScc6WlJaH8SGZmht8bOa7U/1rHtBJS0+EDoFErBfEZpVIVXGg5NvLPtC3szQhHRLt7pp0lIV+E8iOaAKFv8Qh/ZDjCKeVWWYzEklw4dy5hn3Og3zwyF0LjMHMjBSQtLYlrUE+t8q0hMRaNB53Bv6OpydCB1QlJuGIxmRL2Obu7vCIr2r3zSgQkqpqHtIOEFvClSjTySUbe75LKWQFfd8ns2WA2WyOKcBLnlUg8FygmUldHC0jaWpLsLINfizC+rCzoa2mubSSWpKvtYmIQkUiwu+mzO92JJxCQuljOl1aQ0JrsJLQMp7+bXTxpUtDXTygvB2t3e9jXa2+9kKDQl4WhIRsBcgZ1baynSytIXJsIKP0UGY2fOh0yDYYw2j/5+xuPtVzXoRWJefg6raKbb9CSZARIxZdUTg/5+opZc8BktoQd3ZysPZQoQ0JCoc0bfJwvbSyJK/Q15mT590dKS8O8AeEnygZ6E1pAvQWtSBsfJ0obS+IOfdX+60dKKipCnoNyJZFYEtKertEf5KNtnjF6+5Kv86UNJNs/2gxKhcJr+zGXTp27IKxzUK4EIkzNd3d2jvpnpV0sNFo1b4vApUV3Q90MdTeZXD2r7+Mll4S3/4JWl+GeXhGumBOUUBs3ib+V4FMWEoKiv9cxRP7RW286LIFaBXY/d7j+8CFoPnM6rPNy+9tFQEnLmbNQGkZXFgeZg/pxWkHC1YDUN3BLV9HKRJyFaGnm1hYZ6Xf4/aAymbsKbaT8bdPGsN+Ha+Q4XDENJKy8YDXqUykPCXUTG19/lRu1da2QGIko5HKQMA63K4NWVvRzc2nBfuqGIgsxw4fkzInGRDXf7Na2Y3PyjOV7UxYSsgrPPfqw1yaJ5JDJ5TLH0TmjjsZgpFLG65seyc1lGAlkaNVxg6S56WQim/HnqKtSFpJNr7/CAUIQ0IRusgqeMPBxA0fFGp47lcjLX4fWZAVak49SNgRWYMhqzDFwc3XpG8/3SkSjpd2dCS2I/i2Cok9JSCgF7uoGhArHMCQdiWzKYtT/Q1CkKQfJDXf/EPLHT45qyYdkUz6WqIpRrkV9PVpQkjq6WXztKtj4mxfjdv75y6+DookT3b9nGXPAkJ0T8nWP3bMGtBE4u6aBgWRozjtQMxGUO9BH6U0ZSKqvXg4qtRoO7nbMFzpRewh6O7zHrHTZRphUMTyCO2W64/9Wswn+sPZ5rkwxUMRzw5ofgFIV+RavFqsFNBFsDXvi2NFkadLvon6DoNwWSWic9Mm0edWLOY1UaFt5Klwmv8ZfxDNz4RVRAUJSPmMmtDSdCPv5jTUHkqlJaZLzLgSFipH+OxyrkrIDfFQ7QlvHUymfP2dyxqXzYjp/JI4rlQyYzeZkah4yDj9DbUBYHkSVpx0krtoRf5Ov3CO/M6ZHfX7asn5w0Bbh9IrzydhUtErPOspdIigL0woSKgvgvi4y/6s8l86sAn1WVvSQjM2POAxurKtL5iajiqttZFXSBhLqaqTursY3JL38yn+I+RqOHSEiCIOPHUv2ZiMW1iEoP055SKginroaGuNhA/xUzJwe0zWoQo0W+Gcj+Kk/tF8oTfgSgjI3pSFx1Y44Jl/5mv1Zi5bF1NWQcBVqETqv7c1nwWwyC6EJyan9HYLCpCQk5LDS6DHVjVCdp7+bddnSpbxci7ZUjdQvaUng3OAIZSbq9SkJyUdvbQjqsOqy82DWpXNjvs6EstKoxnCS3HkdKT+KKySubzRpNMVC0V5z2+YPuVoThit29nUel63+Hi/X0rq7m8h05+efCQmSauxyMl39D29C9R/rn3rSPZ3SJbQXy8obb4rrJ6IMq8OKSAPWlCy8chlv16NNhyKtXWk4uFdIkBAb1De/y6sl2fTaq25AqP7D9a3+47q17iW64xXRkNVyXdOfqb/6lrvAkJ3F2zWjLRs4ebxRSKDM4rW7ISuyeYMjsqDwUy6XO48yLvP5+7Vr4/IpXNbLEdEE3iJt+erv8npdWooiGkhqDxwQEiSX8AoJVbJzN4oiC4bx2bSwsbYGTjir3PkUAoRAIQsioeo18N0wccVta3i1Im5rEoVuef89wRBit8HEuDiuNEMukOf21iuv8HqtjR7dm2NfX99r6nPGwOpbbo5LI7K0aF2E3uuF0yeEki8BVsLm8AoJLYgbaMTVpTW7v+KtgSiS2fjaK0ETZ6Q333s/qNSq+DRilH7J11u2CsOS2O0qNyR87F5OC+KG03B8NBABsv7pJ50OMhMwcVa1dAUsWLokft+0KCH5+zubBAHJ0CDIhyEZ4uekpZWVIS0uTZXgCxCyXIEG8fTGfLjrgfvi1oBUqB1tzeup+qPQ1dGZ9JCwnsm0IRs/lMy/YlnIga6O1maMgjZEF8UgHF6AcI6q/58HH388Ls6qb0NG97P+maeTv7sZYhVuSGibC15SdCtXcuY/1FfpL+tfCjpvd6TQcx++83bOijgAcWig89//+DNQMX1aXBuwatEiRzItSj20a4dP0jEJfRLGnXFlHXsExyyUrr7qH2+Cjzf8OejzaGuvJx64F6G6BqoWLsIG961hpXQ+1YVQAZFnIk4ikTj9Af/nvu6ue2BhHP0Ql9D2JgZjbkwrLDbUNfj97EnT3dhZbgqGhBt4Ol7LZmZJeTkxrcdx94rlMGgNI4phfRNUtFxEwOxsCJZX3fUjuPWf1oxaI+7ZsQOefeSnMZ3jsfWvwiUzpiUlJF1tZlvplBlyxtH2Et5OTFMg/n3tr8KzuiM4oW7FHyDuZFSQcz383LpRBYTrchYsgPI586N2YC+5dBFkG3OS15KwLDPsk9gG0XnlMZeL34xVaPbDba2Quz0E0fFTpsG6je/Cd+ZfmpCGvPeRh8GQmx8RHfSeH3l+HTz2wrOQX1iQvD6J0w/hups9u7ay+YVFoNTYeb3Iu3/ZAH9+6bm4fYhbH/wZXL36Os56JVKoi93+6eewa+s2qNnl3xmdNm8xTJ46latnSdbuZaR0tJrYKVNnMhwk2z5/n62cXgUs08P7hY4crIHX1q6FU0f5W9N09Zofw8rvXQ9ZoxDiRiPN585zUzvVGk1SW4qgibQhSlcMQEXlbIm7nsRiMYOEYUGulPB6MfrWvPD71+Dbnbvhww0boWbntqjOM21+NcxfUg1zF16etHC4RKhgeMqgRQoWs2MOsxuS1pZzUFBMg359cbko+QyknR2dcLyuHs6cbHLPtj+671vobHXUf1623LEwT0amDiaUlEDxhPH4voqSHoxUE5lUQevTs16QdHd3QAk6VL19fSBXxO/idLOrEJaqBDmaooSTRHP42BKGYd3RjUvOnDoOSoVBbKU0l0GLDC3+RWAkjM0NCVV0kVxsPQ8qlQYsA4zYUmkqZEEMBiO0t7XQ0Ef/MCTOlQwp0qF97MeMGQ82q9hgaWlFzHLoaL/omsZ6chgSRure0a+rsw3a2y+AVpsHrF1stHQSq4WF3LxCOHu60RkG2z5xQ4IOitfyQQ11h9C7lSM8BhGUNHJW9ZkFcPbMCbBaHfso9fX1vOOGxD5ke8c7kTIER2r2gF6fjU/IFEFJA0AU0mzkYIgLXjyk0Q2J2Wz668gX9vf3Qs3B3Riy5oFClsNLiaMoSeioDgHIJAbIyNDD0SN73RPOZDI5u2TZqk7PEHiXv3H47q4OOIygZOgMoNMWgtXMiq2aQkLFZiplHhiyjJxBMJmGV4mUyWTuHSk5SJCYPrlc7ndF2k50ZA/s+wp9FBnk55fBoEkhdj8pIOYBBow5k0Ct0cKh/Tuhp8e75hZdjje9IOGosg2+HOiEfb3dsOebbVzkU1A0EXQZRWAZkIgtLUCxmADkjBGKi0u58br93+7gsu0+ofCg9W0fSLAvClrnP2i1wqEDu6CeIh+ZHIqKy0CrKkBYpFy/JkoS+x1o+SlBKmdyoKhoCugN2XAGw9x9e76AgQHfsTqJRGJ3uCDO38lRcdWNbt/yQZvdbg9ZKkWQTJxUDvmF44BhpFw01NnRig5wL6C/A1K5eGMSLTTUb7MgGAotZGfl4tExINfW1gInjtfSDuQBXyuVyrYvWrKy2mk8vJeeQEBewEPIWn+qZGuor4Gmk3VQiN3P2IJxYKTqLHBM0CI6+/t6yGThOW3Agh24WbpiDxUnU0Hzj2gFBzkolCrQaDJArdZ6dh1w/mwTlwPxZzl8AbP91suyeFqSrZ+9NxYP5yCK6Z9kwnLzCsBgyAFtRqb7nKIkgBm8p/Ql7epqh472Vm6wLty1VPC+WfC52RjMDLjO5QWJE5Q38HBnLG+SBgx1Oj3nOavUGlAi3dQtMVIpFyWJwkP4SkXJLPet5ywFZUktZhOX36KuxB79tMwXEZCHPIHzd8doc7/bIYbJ5PTGiWJSUQQl5LC+NPKPPiAgRZSXfV1sr7SUV/H+N4aExCmPoopmIL1kAAJsEesXEqSJAPmJ2G5pJY/ifT8TNiROUP6Ihz+JbZcW8iXqrwM9GMo5pcXoj4htmNJyAfUmNAr2qCDBF1KN4zXgyJ2IknpCs/FW4H0OuhlPyDAXT9CEh2oRlJQE5Cq8vyEXigkrF+IMixeg1oltmxJC254vwPu6O5wnh50wc1qUKtS3xDYWtFCp6my8nzXhvsAnLR+ObP3sPVoYlQYDx4ptLijr8VOE4+1IXuR37CYCUGgHA8ql/DOqOFE3eYUmXNPuDK8jIBHPpooJEg9YNHi4BfVW1EWQwtvICkgoyPgAlUoQv0A4oi5O5gWSEcCQRaFV7WjT3Qpw7BJJ24rqxfvGq7DO6IQ2fu5GbXJajIOoOxEK3rYEcUMiiijB5P8FGABgj01ZX/7yhQAAAABJRU5ErkJggg==";
		User user = getLoggedInUser(session);
		
		mav.addObject("movieImage", content);
		/*if (movieImage != null) {
			mav.addObject("movieImage", Base64.encodeBytes(film.getMovieImage().getContent()));
		}*/
		
		mav.addObject("commentList", film.getCommentsForUser(user));
		mav.addObject("film", film);
		mav.addObject("MovieImage", new MovieImage());
		
		if(isLoggedIn(session)) {
			boolean userCanComment = film.userCanComment(user);//filmRepo.userCanComment(film, user);
			mav.addObject("userCanComment", userCanComment);
			if(userCanComment){
				mav.addObject("commentForm", new CommentForm());
			}
		}
		
		mav.setViewName("filmDetails");
		
		return mav;
	}
	

	@RequestMapping(value = "{id}/edit", method=RequestMethod.GET)
	public ModelAndView editFilm(HttpSession session, @PathVariable(value = "id") Film film) {
		ModelAndView mav = new ModelAndView();
		List<Genre> genres = filmRepo.getGenres();
		
		mav.addObject("film", film);
		mav.addObject("genreList", genres);
		mav.addObject("filmForm", new FilmForm());
		
		mav.setViewName("editFilm");
		
		return mav;
	}
	
	@RequestMapping(value = "{id}/edit", method=RequestMethod.POST)
	public String editFilmSubmit(HttpSession session, Model model, FilmForm filmForm, Errors errors){
		User user = getLoggedInUser(session);
		DateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date releaseDate = null;
		Film film = filmRepo.get(filmForm.getId());
		
		filmValidator.validate(filmForm, errors);
		
		try {
			releaseDate = outputDateFormat.parse(filmForm.getReleaseDate());
		} catch (ParseException e) {
			errors.rejectValue("releaseDate", "invalid");
		}
		
		if(user.isAdmin()){
			if (!errors.hasErrors()) {
				film.setName(filmForm.getName());
				film.setDirector(filmForm.getDirector());
				film.setLength(filmForm.getLength());
				film.setGenres(filmForm.getGenres());
				film.setDescription(filmForm.getDescription());
				film.setReleaseDate(releaseDate);
			}
			else {
				model.addAttribute("genreList", filmRepo.getGenres());
				model.addAttribute("film", film);
				return "editFilm";
			}
		}
		else {
			return "redirect:../../welcome";
		}
		return "redirect:../filmList";
	}
	

	@RequestMapping(method=RequestMethod.POST)
	public String filmDetails(HttpSession session, CommentForm commentForm, Errors errors, @RequestParam(value = "id", required=false) Film film) {
		
		commentValidator.validate(commentForm, errors);
		if (errors.hasErrors()) {
			errors.rejectValue("text", "required");
			session.setAttribute("errors", errors);
			//return null;
			return "redirect:filmDetails?id=" + commentForm.getFilmId();
		}
		
		User user = getLoggedInUser(session);
		Comment newComment = new Comment.Builder()
								.user(user)
								.film(film)
								.text(commentForm.getText())
								.rate(commentForm.getRating())
								.creationDate(new Date())
								.build();
		
		try {
			film.addComment(newComment);
		}
		catch(UserCantCommentException e) {
			
		}
		
		return "redirect:filmDetails?id=" + commentForm.getFilmId();
	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView editFilmDetails(HttpSession session, @RequestParam(value = "id", required=false) Integer id) {
		ModelAndView mav = new ModelAndView();
		
		Film film = filmRepo.get(id);
		
		mav.addObject("film", film);
		
		mav.setViewName("editFilmDetails");
		
		return mav;
	}
	@RequestMapping(method=RequestMethod.POST)
	public String removeFilm(HttpSession session,@RequestParam(value = "id", required=true) Integer id){
		User user = getLoggedInUser(session);
		if(user.isAdmin()){
			Film film = filmRepo.get(id);
			filmRepo.delete(film);
		}else{
			throw new UserIsntAdminException();
		}
		return "redirect:filmList";
		
	}
	@RequestMapping(method=RequestMethod.POST)
	public String removeCommentFromFilm(
			HttpSession session,
			CommentForm commentForm
			) {
		Film film = filmRepo.get(commentForm.getFilmId());
		
		
		try {
			//film.removeComment();
		}
		catch(UserIsntAdminException e) {
			
		}
		
		return "redirect:filmDetails?id=" + commentForm.getFilmId();
	}
	
	@RequestMapping(value = "filmList", method=RequestMethod.GET)
	public ModelAndView list(HttpSession session, @RequestParam(value = "genre", required=false) Genre genre, @RequestParam(value = "director", required=false) String director) {
		ModelAndView mav = new ModelAndView();
		
		List<Film> filmList = null;
		List<Genre> genreList = filmRepo.getGenres();
		
		if (genre != null) {
			filmList = filmRepo.getFromGenre(genre);
		}
		else{
			filmList = filmRepo.getByReleaseDate();
		}
		
		if (director != null) {
			if (isLoggedIn(session)) {
				filmList = filmRepo.getFromDirector(director);
			}
			else {
				mav.addObject("directorFilterError", "unauthorized");
			}
		}
		
		mav.addObject("filmList", filmList);
		mav.addObject("genreList", genreList);
		
		mav.setViewName("filmList");
		
		return mav;
	}
}

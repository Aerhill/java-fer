package hr.fer.zemris.java.lir.prob3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInit implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Path p = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/files"));
		try {
			List<String> l = Files.list(p).map(r -> r.getFileName().toString()).collect(Collectors.toList());
			sce.getServletContext().setAttribute("allFiles", l);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}

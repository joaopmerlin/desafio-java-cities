package br.com.joaomerlin.cities.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.joaomerlin.cities.factory.ExportFactory;
import br.com.joaomerlin.cities.model.Format;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportFactory exportFactory;

    @GetMapping(value = "{format}")
    public void exportJson(@PathVariable String format, HttpServletResponse response) throws Exception {
        Format formatEnum = Format.getFormat(format);
        if (formatEnum == null) {
            throw new RuntimeException("Format not supported");
        }
        response.setContentType(formatEnum.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=export." + formatEnum.getExt());
        exportFactory.getService(formatEnum).export(response.getOutputStream());
    }

}

package br.com.joaomerlin.cities.controller;

import br.com.joaomerlin.cities.model.Format;
import br.com.joaomerlin.cities.service.ExportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("export")
public class ExportController {

    private final Map<Format, ExportService> exportServices;

    public ExportController(List<ExportService> exportServices) {
        this.exportServices = exportServices.stream().collect(Collectors.toMap(ExportService::getFormat, e -> e));
    }

    @GetMapping(value = "{format}")
    public void exportJson(@PathVariable String format, HttpServletResponse response) throws Exception {
        Format formatEnum = Format.getFormat(format);
        if (formatEnum == null) {
            throw new RuntimeException("Format not supported");
        }
        response.setContentType(formatEnum.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=export." + formatEnum.getExt());
        exportServices.get(formatEnum).export(response.getOutputStream());
    }

}

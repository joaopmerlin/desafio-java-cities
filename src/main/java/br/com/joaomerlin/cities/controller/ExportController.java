package br.com.joaomerlin.cities.controller;

import br.com.joaomerlin.cities.factory.ExportFactory;
import br.com.joaomerlin.cities.model.Format;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportFactory exportFactory;

    @GetMapping(value = "{format}")
    @ApiOperation("Export file")
    public void export(@ApiParam("JSON or CSV") @PathVariable String format,
                       HttpServletResponse response) throws Exception {
        Format formatEnum = Format.getFormat(format);
        if (formatEnum == null) {
            response.sendError(400, "Invalid format");
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(formatEnum.getContentType());
            response.setHeader("Content-Disposition", "filename=export." + formatEnum.getExt());
            exportFactory.getService(formatEnum).export(response.getOutputStream());
        }
    }

}

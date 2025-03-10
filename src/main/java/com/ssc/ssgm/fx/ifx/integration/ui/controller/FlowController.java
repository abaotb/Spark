
package com.ssc.ssgm.fx.ifx.integration.ui.controller;

import com.ssc.ssgm.fx.ifx.integration.common.Response;
import com.ssc.ssgm.fx.ifx.integration.core.config.*;
import com.ssc.ssgm.fx.ifx.integration.core.flow.FlowContext;
import com.ssc.ssgm.fx.ifx.integration.curd.service.FlowConfigService;
import com.ssc.ssgm.fx.ifx.integration.ui.dto.FlowDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ssc.ssgm.fx.ifx.integration.core.flow.FlowTransActionType;
import com.ssc.ssgm.fx.ifx.integration.core.parser.ParserEnum;

@RestController
@RequestMapping(value = "/api/flow")
@ApiOperation("flow api")
@Slf4j
public class FlowController {

    @Autowired
    FlowConfigService flowConfigService;

    @Autowired
    FlowContext flowContext;

    @ApiOperation("get_flow_types")
    @GetMapping("/get_flow_types")
    public Response<List<KeyValue>> getFlowTypes() {
        List<FlowTransActionType> typeEunmList = Arrays.asList(FlowTransActionType.values());
        List<KeyValue> typeList = typeEunmList.stream().map(e ->{
            KeyValue keyValue = new KeyValue();
            keyValue.setLabel(e.toString());
            keyValue.setName(e.toString());
            return keyValue;
        } ).collect(Collectors.toList());
        return Response.success(typeList);
    }

    @ApiOperation("get_inbounds")
    @GetMapping("/get_inbounds")
    public Response<List<KeyValue>> getInbounds() {
        List<InboundConfig> inboundConfigList = flowContext.getInboundConfigs();
        List<KeyValue> inbounds = inboundConfigList.stream().map(e ->{
            KeyValue tKeyValue = new KeyValue();
            tKeyValue.setLabel(e.getName());
            tKeyValue.setName(e.getName());
            return tKeyValue;
        }).collect(Collectors.toList());
        return Response.success(inbounds);
    }

    @ApiOperation("get_parsers")
    @GetMapping("/get_parsers")
    public Response<List<KeyValue>> getParsers() {
        List<ParserEnum> typeEunmList = Arrays.asList(ParserEnum.values());
        List<KeyValue> typeList = typeEunmList.stream().map(e ->{
            KeyValue keyValue = new KeyValue();
            keyValue.setName(e.toString());
            keyValue.setLabel(e.toString());
            return keyValue;
        } ).collect(Collectors.toList());
        return Response.success(typeList);
    }

    @ApiOperation("get_key_mappers")
    @GetMapping("/get_key_mappers")
    public Response<List<KeyValue>> get_key_mappers() {
        List<KeyMapperConfig> keyMapperConfigs = flowContext.getKeyMapperConfigs();
        List<KeyValue> keyValues = keyMapperConfigs.stream().map(e ->{
            KeyValue keyValue = new KeyValue();
            keyValue.setName(e.getName());
            keyValue.setLabel(e.getName());
            return keyValue;
        } ).collect(Collectors.toList());
        return Response.success(keyValues);
    }

    @ApiOperation("get_formatters")
    @GetMapping("/get_formatters")
    public Response<List<KeyValue>> get_formatters() {
        List<FormatterConfig> formatterConfigs = flowContext.getFormatterConfigs();
        List<KeyValue> keyValues = formatterConfigs.stream().map(e ->{
            KeyValue keyValue = new KeyValue();
            keyValue.setName(e.getName());
            keyValue.setLabel(e.getName());
            return keyValue;
        } ).collect(Collectors.toList());
        return Response.success(keyValues);
    }


    @ApiOperation("get_outbounds")
    @GetMapping("/get_outbounds")
    public Response<List<KeyValue>> getOutbounds() {
        List<OutboundConfig> outboundConfigList = flowContext.getOutboundConfigs();
        List<KeyValue> outbounds = outboundConfigList.stream().map(e ->{
            KeyValue tKeyValue = new KeyValue();
            tKeyValue.setLabel(e.getName());
            tKeyValue.setName(e.getName());
            return tKeyValue;
        }).collect(Collectors.toList());
        return Response.success(outbounds);
    }

    @ApiOperation("list")
    @GetMapping("/list")
    public Response<List<FlowConfig>> list() {
        flowContext.loadFlows();
        List<FlowConfig> flowConfigs = flowContext.getFlowConfigs();
        return Response.success(flowConfigs);
    }

    @ApiOperation("create")
    @GetMapping("/create")
    public Response<?> create(@RequestParam("flowConfig") FlowConfig flowConfig) {
        String id = UUID.randomUUID().toString().replace("-","");
        flowConfig.setId(id);
        if ( flowConfigService.addConfig(flowConfig) !=1){
                return Response.fail();
        }
        flowContext.addFlowConfig(flowConfig);
        return Response.success();
    }

    @ApiOperation("start")
    @PostMapping("/start")
    public Response<?> start(@RequestBody FlowDTO flowDTO) {
        return Response.success();
    }

    @ApiOperation("pause")
    @PostMapping("/pause")
    public Response<?> pause(@RequestBody FlowDTO flowDTO) {
        return Response.success();
    }

    @ApiOperation("stop")
    @PostMapping("/stop")
    public Response<?> stop(@RequestBody FlowDTO flowDTO) {
        return Response.success();
    }

}

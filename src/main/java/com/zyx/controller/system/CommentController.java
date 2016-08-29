package com.zyx.controller.system;

import com.zyx.config.BaseResponse;
import com.zyx.rpc.system.CommentFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * @author XiaoWei
 * @version V 1.0
 * @package com.zyx.controller.system
 * Create by XiaoWei on 2016/8/29
 */

@RestController
@RequestMapping("/v1/comment")
@Api(description = "评论接口")
public class CommentController {

    @Autowired
    private CommentFacade commentFacade;

    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ApiOperation(value = "首推-按照模块获取所有首推", notes = "首推-按照模块获取所有首推", response = BaseResponse.class)
    public ModelAndView addComment(@RequestParam(value = "comment_type") Integer commentType,
                                   @RequestParam(value = "comment_id") Integer comment_id,
                                   @RequestParam(value = "comment_account") Integer comment_account,
                                   @RequestParam(value = "comment_content") String comment_content,
                                   @RequestParam(value = "comment_state", required = false, defaultValue = "0") Integer comment_state) {
        Map<String, Object> map = commentFacade.addComment(commentType, comment_id, comment_content, comment_account, comment_state);
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);

    }
}

package com.aiwan.client.Resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;

/**
 * @author dengzebiao
 * @since 2019.6.13
 * 客户端资源查看
 * */
@Resource("staticresource/clientResource.xls")
public class ClientViewResource {
    /** 资源类型 */
    @CellMapping(name = "type")
    private String type;

    /** id 类型中的唯一id*/
    @CellMapping(name = "id")
    private int id;

    /** 内容 */
    @CellMapping(name = "content")
    private String content;

    public String getType() {
        return type;
    }

    public ClientViewResource setType(String type) {
        this.type = type;
        return this;
    }

    public int getId() {
        return id;
    }

    public ClientViewResource setId(int id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ClientViewResource setContent(String content) {
        this.content = content;
        return this;
    }
}

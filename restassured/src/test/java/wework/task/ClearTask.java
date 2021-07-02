package wework.task;

import io.restassured.response.Response;
import wework.api.contact.DepartmentApi;
import wework.api.contact.DepartmentMemberApi;
import wework.api.contact.TagApi;
import wework.filter.TokenFilter;

import java.util.ArrayList;

public class ClearTask {
    public static void clearDepartment(Long parentId){
        try{
            ArrayList<Long> departmentIds = DepartmentApi.getAllDepartmentInfo(parentId).path("department.id");
            int size = departmentIds.size();
            for(int i=0;i<size;i++){
                long id = Long.valueOf(String.valueOf(departmentIds.get(i)));
                if(parentId==id){
                    continue;
                }
                DepartmentApi.deleteDepartment(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void clearMemberDepartment(Long departmentIdInit){
        try{
            //获取该部门所有用户
            Response res = DepartmentMemberApi.getDepartmentUserList(departmentIdInit,1);
            ArrayList<String> userIdList = res.path("userlist.userid");
            //删除所有该部门下的用户
            DepartmentMemberApi.deleteMemberList(userIdList);
            //删除该部门
            DepartmentApi.deleteDepartment(departmentIdInit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void clearTags(TokenFilter tokenFilter){
        try{
            //获取标签列表
            Response res = TagApi.getTagList(tokenFilter);
            ArrayList<Integer> tagIds = res.path("taglist.tagid");
            //删除所有标签
            for(int tagid:tagIds){
                TagApi.deleteTag(tokenFilter,tagid);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

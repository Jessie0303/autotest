package wework.filter;

import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import wework.api.contact.TokenApi;

public class TokenFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        filterableRequestSpecification.queryParam("access_token", TokenApi.getContactToken());
        Response responseOrigin = filterContext.next(filterableRequestSpecification,filterableResponseSpecification);
        Response responseAction = new ResponseBuilder().clone(responseOrigin).build();
        return responseAction;
    }
}

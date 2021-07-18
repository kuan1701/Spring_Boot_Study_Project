<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="ml-5 col-form-label">Username:</label>
            <div class="col-sm-4">
                <input type="text" name="username" class="form-control" placeholder="Username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="ml-5 col-form-label">Password:</label>
            <div class="col-sm-4">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="ml-5 col-form-label">Email:</label>
                <div class="col-sm-4">
                    <input type="email" name="email" class="form-control" placeholder="Email"/>
                </div>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button class="btn btn-primary ml-3" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>
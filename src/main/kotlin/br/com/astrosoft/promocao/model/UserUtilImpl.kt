package br.com.astrosoft.promocao.model

import br.com.astrosoft.framework.model.IUser
import br.com.astrosoft.framework.model.IUserUtil

class UserUtilImpl : IUserUtil {
    override fun findUser(username: String): IUser? = saci.findUser(username)
}
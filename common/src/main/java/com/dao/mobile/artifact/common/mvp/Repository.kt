package com.dao.mobile.artifact.common.mvp

/**
 * Created in 17/08/18 16:02.
 *
 * @author Diogo Oliveira.
 */
abstract class Repository<L, R>(protected val local: L, protected val remote: R)

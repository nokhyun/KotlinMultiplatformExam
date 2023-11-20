package di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val platformModule = module {
    singleOf(::Platform)
}

expect class Platform(){
    val name: String
}
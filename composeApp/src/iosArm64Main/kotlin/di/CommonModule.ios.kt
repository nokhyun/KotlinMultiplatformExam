package di

import db.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val sqldelightModule: Module = module {
    single<DatabaseDriverFactory> { DatabaseDriverFactory()}
}
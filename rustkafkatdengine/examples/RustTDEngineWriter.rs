use taos::{Taos, TaosBuilder};
use anyhow::Result;

#[tokio::main]
async fn main() -> Result<()> {
    let dsn = std::env::var("TDENGINE_CLOUD_DSN")?;
    let taos = TaosBuilder::from_dsn(dsn)?.build()?;
    let db = "mydb";
    taos.exec(format!("CREATE DATABASE IF NOT EXISTS `{}`", db)).await?;
    taos.exec(format!("USE `{}`", db)).await?;
    taos.exec(format!("CREATE TABLE IF NOT EXISTS `mytable` (`ts` TIMESTAMP, `value` FLOAT) TAGS (`tag1` BINARY(64), `tag2` BINARY(64))")).await?;
    taos.exec(format!("INSERT INTO `mytable` VALUES (NOW, 1.23) TAGS ('tag1', 'tag2')")).await?;
    Ok(())
}


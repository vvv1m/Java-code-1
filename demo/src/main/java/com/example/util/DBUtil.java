package com.example.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接工具类（使用 HikariCP 连接池）
 */
public class DBUtil {
    private static HikariDataSource dataSource;
    private static boolean initialized = false;

    static {
        try {
            initDataSource();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("数据库连接池初始化失败: " + e.getMessage());
        }
    }

    /**
     * 初始化数据源
     */
    private static void initDataSource() {
        try {
            Properties props = new Properties();
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            
            if (in == null) {
                System.err.println("❌ 错误：无法找到 db.properties 文件");
                System.err.println("📁 请确保文件位于：src/main/resources/db.properties");
                throw new RuntimeException("无法找到 db.properties 文件");
            }
            
            props.load(in);
            in.close();

            // 创建 HikariCP 配置
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("jdbc.url"));
            config.setUsername(props.getProperty("jdbc.username"));
            config.setPassword(props.getProperty("jdbc.password"));
            config.setDriverClassName(props.getProperty("jdbc.driver"));
            
            // 连接池配置
            config.setMaximumPoolSize(
                Integer.parseInt(props.getProperty("hikari.maximumPoolSize", "10"))
            );
            config.setMinimumIdle(
                Integer.parseInt(props.getProperty("hikari.minimumIdle", "5"))
            );
            config.setConnectionTimeout(
                Long.parseLong(props.getProperty("hikari.connectionTimeout", "30000"))
            );
            config.setIdleTimeout(
                Long.parseLong(props.getProperty("hikari.idleTimeout", "600000"))
            );
            config.setMaxLifetime(
                Long.parseLong(props.getProperty("hikari.maxLifetime", "1800000"))
            );
            
            // 连接池名称
            config.setPoolName("GalleryHikariPool");
            
            // 数据源属性
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");

            // 创建数据源
            dataSource = new HikariDataSource(config);
            
            // 测试连接
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ 数据库连接成功!");
                System.out.println("📊 连接池状态：");
                System.out.println("   - 活跃连接数: " + dataSource.getHikariPoolMXBean().getActiveConnections());
                System.out.println("   - 总连接数: " + dataSource.getHikariPoolMXBean().getTotalConnections());
                System.out.println("   - 空闲连接数: " + dataSource.getHikariPoolMXBean().getIdleConnections());
                initialized = true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ 数据库连接池初始化失败: " + e.getMessage());
            throw new RuntimeException("数据库连接池初始化失败", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws SQLException {
        if (dataSource == null || !initialized) {
            throw new SQLException("数据源未初始化或初始化失败");
        }
        
        Connection conn = dataSource.getConnection();
        
        if (conn == null) {
            throw new SQLException("无法从连接池获取连接");
        }
        
        return conn;
    }

    /**
     * 关闭连接（归还到连接池）
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close(); // HikariCP 会自动归还连接到池中
            } catch (SQLException e) {
                System.err.println("❌ 关闭连接失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据源（应用关闭时调用）
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            System.out.println("🔒 正在关闭数据库连接池...");
            dataSource.close();
            initialized = false;
            System.out.println("✅ 数据库连接池已关闭");
        }
    }
    
    /**
     * 获取连接池状态信息
     */
    public static String getPoolStatus() {
        if (dataSource == null || !initialized) {
            return "数据源未初始化";
        }
        
        return String.format(
            "连接池状态 - 活跃:%d, 空闲:%d, 总计:%d, 等待:%d",
            dataSource.getHikariPoolMXBean().getActiveConnections(),
            dataSource.getHikariPoolMXBean().getIdleConnections(),
            dataSource.getHikariPoolMXBean().getTotalConnections(),
            dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection()
        );
    }
    
    /**
     * 检查数据源是否已初始化
     */
    public static boolean isInitialized() {
        return initialized && dataSource != null && !dataSource.isClosed();
    }
}
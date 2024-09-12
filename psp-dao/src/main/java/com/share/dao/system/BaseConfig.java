package com.share.dao.system;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置基类，该类实际持有所有的配置，子类只是提供代理访问方法
 */
abstract class BaseConfig {

	// 所有的配置均保存在该 ConcurrentHashMap 中以保证线程安全
	private static final Map<String, String> configs = new ConcurrentHashMap<>();


	/**
	 * 添加配置到公共Map中
	 *
	 * @param key
	 * @param value
	 */
	public static void addConfig(String key, String value) {
		configs.put(key, value);
	}

	/**
	 * 移除以指定前缀开头的所有配置
	 *
	 * @param prefix
	 */
	public static void removeConfigsByPrefix(String prefix) {
		configs.entrySet().removeIf(entry -> entry.getKey().startsWith(prefix));
	}

	/**
	 * 按String类型获取配置值
	 *
	 * @param keyName
	 * @return
	 */
	public static String getConfig(String keyName) {
		return configs.get(keyName);
	}

	/**
	 * 以Integer类型获取配置值
	 *
	 * @param keyName
	 * @return
	 */
	public static Integer getConfigInt(String keyName) {
		String value = configs.get(keyName);
		return value != null ? Integer.parseInt(value) : null;
	}

	/**
	 * 以BigDecimal类型获取配置值
	 *
	 * @param keyName
	 * @return
	 */
	public static BigDecimal getConfigBigDec(String keyName) {
		String value = configs.get(keyName);
		return value != null ? new BigDecimal(value) : null;
	}

	/**
	 * 子类需要实现该方法，并返回其配置的前缀
	 *
	 * @return 配置前缀
	 */
	public abstract String getPrefix();

	// 注意：这里不再包含 reloadConfig 方法，因为该方法的实现应该由具体的服务类来处理
	// 例如，ConfigService 可以有一个 reloadConfig 方法，它调用 removeConfigsByPrefix 然后再重新加载配置
}



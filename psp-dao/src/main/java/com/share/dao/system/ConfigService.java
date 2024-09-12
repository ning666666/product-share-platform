package com.share.dao.system;

import com.share.dao.model.PspSystem;
import com.share.dao.service.PspSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 该类用于自动初始化数据库配置到BaseConfig中，以便BaseConfig的子类调用
 */
@Service
public class ConfigService {


	@Autowired
	private PspSystemConfigService pspSystemConfigService;

	// 不允许实例化
	private ConfigService() {

	}

	/**
	 * 根据 prefix 重置该 prefix 下所有设置
	 *
	 * @param prefix
	 */
	public void reloadConfig(String prefix) {
		List<PspSystem> list = pspSystemConfigService.queryAll();
		if (!CollectionUtils.isEmpty(list))
			for (PspSystem item : list) {
				// 符合条件，添加
				if (item.getKeyName().startsWith(prefix))
					BaseConfig.addConfig(item.getKeyName(), item.getKeyValue());
			}
	}

	/**
	 * 读取全部配置
	 */
	private void inistConfigs() {
		// 这里可以调用私有方法来加载配置
		loadConfigs();
	}

	// 私有方法，用于加载配置
	private void loadConfigs() {
		List<PspSystem> list = pspSystemConfigService.queryAll();
		if (!CollectionUtils.isEmpty(list)) {
			for (PspSystem item : list) {
				BaseConfig.addConfig(item.getKeyName(), item.getKeyValue());
			}
		}
	}
}
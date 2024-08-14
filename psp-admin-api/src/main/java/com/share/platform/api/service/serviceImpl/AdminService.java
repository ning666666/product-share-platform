package com.share.platform.api.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.mapper.PspAdminMapper;
import com.share.platform.api.model.PspAdmin;
import com.share.platform.api.model.PspAdmin.Column;
import com.share.platform.api.model.PspAdminExample;
import com.share.platform.api.utils.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AdminService {
	private final Column[] result = new Column[] {Column.id, Column.username, Column.avatar, Column.roleIds };
	@Resource
	private PspAdminMapper adminMapper;

	private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");


	@Value("${file.upload.path}")
	private String fileSavePath;


	/**
	 * 限制图片大小1MB
	 */
	private static final long MAX_FILE_SIZE = 1048576;

	public List<PspAdmin> findAdmin(String username) {
		PspAdminExample example = new PspAdminExample();
		example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
		return adminMapper.selectByExample(example);
	}

	public PspAdmin findAdmin(Integer id) {
		return adminMapper.selectByPrimaryKey(id);
	}

	public List<PspAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
		PspAdminExample example = new PspAdminExample();
		PspAdminExample.Criteria criteria = example.createCriteria();

		if (!StringUtils.isEmpty(username)) {
			criteria.andUsernameLike("%" + username + "%");
		}
		criteria.andDeletedEqualTo(false);

		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, limit);
		return adminMapper.selectByExampleSelective(example, result);
	}

	public int updateById(PspAdmin admin) {
		admin.setUpdateTime(new Date());
		return adminMapper.updateByPrimaryKeySelective(admin);
	}

	public void deleteById(Integer id) {
		adminMapper.logicalDeleteByPrimaryKey(id);
	}

	public void add(PspAdmin admin) {
		admin.setCreateTime(new Date());
		admin.setUpdateTime(new Date());
		adminMapper.insertSelective(admin);
	}

	public PspAdmin findById(Integer id) {
		return adminMapper.selectByPrimaryKeySelective(id, result);
	}

	public List<PspAdmin> allAdmin() {
		PspAdminExample example = new PspAdminExample();
		example.or().andDeletedEqualTo(false);
		return adminMapper.selectByExample(example);
	}

	public ResultVo storageUpload(MultipartFile file) {
		return fileUpload(file);
	}

	public ResultVo<?> fileUpload(MultipartFile file) {
		try {
			// 获取文件名
			String fileName = file.getOriginalFilename();
			// 检查文件后缀名
			String fileExtension = getFileExtension(fileName);
			if (ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension)) {
				// 检查文件大小
				long fileSize = file.getSize();
				if (fileSize < MAX_FILE_SIZE) {
					// 生成新文件名
					String newFileName = UUID.randomUUID().toString() + fileExtension;
					// 创建文件目录
					File dest = new File(fileSavePath + newFileName);
					if (!dest.getParentFile().exists()) {
						dest.getParentFile().mkdirs();
					}
					// 保存文件
					file.transferTo(dest);
					// 获取保存后的文件路径
					String filePath = fileSavePath + newFileName;

					log.info(String.format("image upload success address:{%s}", filePath));
					return ResultVo.buildData(ResultCode.UPLOAD_SUCCESS, filePath);
				} else {
					log.error(String.format("image upload fail size:{%s}", fileSize));
					return ResultVo.buildCode(ResultCode.UPLOAD_FAILED, "图片太大，最大允许：" + MAX_FILE_SIZE + "字节");
				}
			} else {
				log.error(String.format("image upload fail type:{%s}", fileExtension));
				return ResultVo.buildCode(ResultCode.UPLOAD_FAILED, "不允许的图片类型，只允许：" + ALLOWED_IMAGE_EXTENSIONS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultVo.buildCode(ResultCode.RUNTIME_ERROR);
		}
	}

	public String getFileExtension(String fileName) {
		if (fileName == null) {
			return "";
		}
		int lastIndexOfDot = fileName.lastIndexOf('.');
		return lastIndexOfDot != -1 ? fileName.substring(lastIndexOfDot) : "";
	}

}

/* ----------------------------------------------------------------------
 * Copyright (c) 2011 by RMSI.
 * All Rights Reserved
 *
 * Permission to use this program and its related files is at the
 * discretion of RMSI Pvt Ltd.
 *
 * The licensee of RMSI Software agrees that:
 *    - Redistribution in whole or in part is not permitted.
 *    - Modification of source is not permitted.
 *    - Use of the source in whole or in part outside of RMSI is not
 *      permitted.
 *
 * THIS SOFTWARE IS PROVIDED ''AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL RMSI OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * ----------------------------------------------------------------------
 */

package com.rmsi.mast.studio.web.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmsi.mast.studio.domain.Outputformat;
import com.rmsi.mast.studio.service.OutputformatService;

/**
 * @author Aparesh.Chakraborty
 *
 */
@Controller
public class OutputformatController {

	@Autowired
	OutputformatService outputformatService;
	
	@RequestMapping(value = "/studio/outputformat/", method = RequestMethod.GET)
	@ResponseBody
    public List<Outputformat> list(){
		return 	outputformatService.findAllOutputformat();	
	}
	
	
	@RequestMapping(value = "/studio/outputformat/{id}", method = RequestMethod.GET)
	@ResponseBody
    public Outputformat getOutputformatById(@PathVariable String id){		
		return 	outputformatService.findOutputformatByName(id);	
	}
    	
	
	@RequestMapping(value = "/studio/outputformat/delete/", method = RequestMethod.GET)
	@ResponseBody
    public void deleteOutputformat(){
		outputformatService.deleteOutputformat();
	}
	
	
	@RequestMapping(value = "/studio/outputformat/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
    public void deleteOutputformatById(@PathVariable Long id){
		outputformatService.deleteOutputformatById(id);	
	}
	
	@RequestMapping(value = "/studio/outputformat/create", method = RequestMethod.POST)
	@ResponseBody
    public void createOutputformat(Outputformat outputformat){
		outputformatService.addOutputformat(outputformat);	
	}
	
	@RequestMapping(value = "/studio/outputformat/edit", method = RequestMethod.POST)
	@ResponseBody
    public void editOutputformat(Outputformat outputformat){
		outputformatService.updateOutputformat(outputformat);	
	}
	
	
}

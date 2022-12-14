/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mars.example;

import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaojing
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

	@RestController
	class EchoController {

		@Resource
		private NacosDiscoveryProperties nacosDiscoveryProperties;

		@GetMapping("/")
		public ResponseEntity index() {
			System.out.println("entry index func");
			return new ResponseEntity("index error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@GetMapping("/test")
		public ResponseEntity test() {
			System.out.println("entry test func");
			return new ResponseEntity("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@GetMapping("/sleep")
		public String sleep() {
			System.out.println("entry sleep func");
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "ok";
		}

		@GetMapping("/echo/{string}")
		public String echo(@PathVariable String string) {
			System.out.printf("entry echo func, string=%s\n", string);
			return "hello Nacos Discovery " + string;
		}

		@GetMapping("/divide")
		public String divide(@RequestParam Integer a, @RequestParam Integer b) {
			System.out.printf("entry divide func, a=%s, b=%s\n", a, b);
			if (b == 0) {
				return String.valueOf(0);
			} else {
				return String.valueOf(a / b);
			}
		}

		@GetMapping("/zone")
		public String zone() {
			System.out.println("entry zone func");
			Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
			return "provider zone " + metadata.get("zone");
		}

		@GetMapping("/delay/{ms}")
		public String delay(@PathVariable long ms) {
			System.out.printf("entry delay func, delay=%s(ms)\n", ms);
			try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "ok, after " + ms + "(ms)";
		}

	}

}

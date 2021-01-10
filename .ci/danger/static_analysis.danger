
checkstyle_format.base_path = Dir.pwd

# ktlint

Dir.glob("./**/ktlint*SourceSetCheck.xml").each do |file|
  checkstyle_format.report file
end

# detekt

Dir.glob("./**/detekt.xml").each do |file|
  checkstyle_format.report file
end

# JUnit

Dir.glob("./**/test-results/**/*.xml").each do |file|
  junit.parse file
  junit.report
end

# Android Lint

LINT_RESULTS_FILE = 'app/build/reports/lint-results-devRelease.xml'.freeze

if File.exists?(LINT_RESULTS_FILE)
  android_lint.skip_gradle_task = true
  android_lint.report_file = LINT_RESULTS_FILE
  android_lint.severity = "Warning"
  android_lint.filtering = true
  android_lint.lint(inline_mode: true)
end
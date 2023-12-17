{{/* Generate basic labels */}}
{{- define "chart.fullname" -}}
{{- printf "%s-%s" .Release.Name .Chart.Name }}
{{- end -}}

{{/* Current date */}}
{{- define "current-date" -}}
{{- now | htmlDate }}
{{- end -}}

{{/* Version */}}
{{- define "version-label" -}}
{{- printf "version-%s" .Chart.AppVersion }}
{{- end -}}